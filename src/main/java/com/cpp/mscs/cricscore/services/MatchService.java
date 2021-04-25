package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.*;
import com.cpp.mscs.cricscore.repositories.MatchPlayedRepo;
import com.cpp.mscs.cricscore.repositories.MatchRepo;
import com.cpp.mscs.cricscore.repositories.TeamName;
import com.cpp.mscs.cricscore.repositories.TeamRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static com.cpp.mscs.cricscore.controller.MatchController.INNINGS_TYPE;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/4/21
 * Time:  13:28
 */

@Service
public class MatchService {

    Logger LOGGER = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    MatchRepo matchRepo;

    @Autowired
    MatchPlayedRepo matchPlayedRepo;

    @Autowired
    TeamRepo teamRepo;

    @Autowired
    PlayerService playerService;

    @Autowired
    CurrentPlayingPlayersService currentPlayingPlayersService;

    ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public void addMatch(Match match) {

        LOGGER.info("Adding Match details to DB {}", match.getMatchId());

        long matchId = matchRepo.save(match).getMatchId();
        MatchSummary matchSummary = new MatchSummary();
        matchSummary.setFirstInningsOver(false);
        matchSummary.setLive(true);
        TeamName teamAName = teamRepo.findByTeamId((long) match.getTeamAId());
        TeamName teamBName = teamRepo.findByTeamId((long) match.getTeamBId());
        matchSummary.setMatchTitile(teamAName.getTeamName()+"-"+teamBName.getTeamName());
        try {
            addMatchSummaryData(matchSummary, matchId, (long) match.getMatchVenuecityId());
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Failed to add Match Summary Data");
        }
        addSelectedPlayers(match, matchId);
    }

    @Transactional
    public void addSelectedPlayers(Match match, long matchId) {

        LOGGER.info("Adding Selected Players to DB {}", match.getMatchId());

        List<MatchPlayer> collections = new ArrayList<>();
        for(String playeruuid : match.getTeamAplayers()){
            collections.add(new MatchPlayer(matchId, playeruuid, match.getTeamAId()));
        }
        try{
            matchPlayedRepo.saveAll(collections);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Failed to Add Match Team A players to repository {}", collections);
        }


        for(String playeruuid : match.getTeamBplayers()){
            collections.add(new MatchPlayer(matchId, playeruuid, match.getTeamBId()));
        }
        try {
            matchPlayedRepo.saveAll(collections);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Failed to Add Match Team B players to repository {}", collections);
        }

    }

    @Transactional
    public void addPlayer(long mathcId, String uuId, MatchPlayer player){
        LOGGER.info("Adding Players to DB {} ", player);
        player.setPrimaryKey(new ReferencePrimaryKey(mathcId, uuId));
        try {
            matchPlayedRepo.save(player);
        }catch (Exception e){
            LOGGER.error("Failed to Players to DB {}", player);
        }

    }

    @Transactional
    public void updateMatch(long mathcId, Match match) throws IOException {
        LOGGER.info("Updating Match details");
        try{
            matchRepo.updateResult(match.getResult(), match.getWinningTeamId(), match.getTotalScore(), mathcId);
        }catch (Exception e){
            LOGGER.error("Failed to update match details");
        }

        String matchSummaryData = getLiveMatchesIdsinTheCity(match.getMatchVenuecityId()).get(String.valueOf(mathcId));
        MatchSummary matchSummary = getMatchSummary(matchSummaryData);

        if(match.getResult() != null && !match.getResult().isEmpty()){
            matchSummary.setResult(match.getResult());
            matchSummary.setLive(false);
            matchSummary.setFirstInningsOver(true);
        }

        addMatchSummaryData(matchSummary, mathcId, (long) match.getMatchVenuecityId());
    }

    @Cacheable("matchInning")
    public Optional<Match> getMatch(long matchId) {
        LOGGER.info("Get match by Match Id {}", matchId);
        try {
            return matchRepo.findById(matchId);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Failed to fetch match for the Match ID : {}" , matchId);
            return Optional.ofNullable(null);
        }

    }

    @Cacheable("matchesInCity")
    public List<MatchSummary> getMatchesinTheCity(long cityId){
        LOGGER.info("Get matches by city Id {}", cityId);

        Map<String, String> matchIds = getLiveMatchesIdsinTheCity(cityId);
        List<MatchSummary> matchSummaries = new ArrayList<>();

        matchIds.forEach((k, v) -> {
            MatchSummary matchSummary = null;
            try {
                matchSummary = mapper.readValue(v, MatchSummary.class);
                CurrentPlaying firstInning = currentPlayingPlayersService.getCurrentPlayers(k+"-"+INNINGS_TYPE[0]);
                if(firstInning != null){
                    matchSummary.setFirstInningsScore(firstInning);
                }
                CurrentPlaying secondInning = currentPlayingPlayersService.getCurrentPlayers(k+"-"+INNINGS_TYPE[1]);
                if(secondInning != null) {
                    matchSummary.setSecondInningsScore(secondInning);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            matchSummaries.add(matchSummary);
        });
        return matchSummaries;
    }

    @Cacheable("liveScore")
    public Map<String, String> getLiveMatchesIdsinTheCity(long cityId){
        Jedis jedis = JedisPoolHelper.getResource();
        return jedis.hgetAll("city-"+cityId);
    }

    public MatchSummary getMatchSummary(String matchSummary) throws JsonProcessingException {
        return mapper.readValue(matchSummary, MatchSummary.class);
    }


    public void addMatchSummaryData(MatchSummary matchSummary, Long matchId, Long cityId) throws IOException {
        HashMap<String, String> matchSummaryHashMap = new HashMap<>();
        matchSummaryHashMap.put(matchId.toString(), mapper.writeValueAsString(matchSummary));
        Jedis jedis = JedisPoolHelper.getResource();
        jedis.hmset("city-"+cityId, matchSummaryHashMap);
    }

    public InningsScoreCard getInningsScoreCard(Long matchId, Long battingTeamId) {

        List<MatchPlayer> players = matchPlayedRepo.findAllByMatchId(matchId);

        Map<Long, List<MatchPlayer>> teamAndMatchPlayers = new HashMap<>();
        for (MatchPlayer player : players) {
            teamAndMatchPlayers.computeIfAbsent((long) player.getTeamId(), k -> new ArrayList<>()).add(player);
        }
        InningsScoreCard inningsScoreCard = new InningsScoreCard();
        teamAndMatchPlayers.entrySet().forEach(
                team -> {
                    if(team.getKey().equals(battingTeamId)){
                        inningsScoreCard.setBattingplayerScoreCard(PlayerScoreCard.of(mapNametoPlayerScore(team.getValue())));
                    }else{
                        inningsScoreCard.setBowlingPlayerScoreCard(PlayerScoreCard.of(mapNametoPlayerScore(team.getValue())));
                    }
                }
        );
        return inningsScoreCard;
    }

    private Map<String, MatchPlayer> mapNametoPlayerScore(List<MatchPlayer> players){
        HashMap<String, MatchPlayer> playerMap = new HashMap<>();
        players.forEach(p -> {
                    String playerName = playerService.getPlayer(p.getPrimaryKey().getPlayeruuid()).getName();
                    playerMap.put(playerName, p);
                }
        );
        return playerMap;
    }
}