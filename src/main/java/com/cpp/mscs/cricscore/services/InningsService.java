package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.*;
import com.cpp.mscs.cricscore.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.MessageFormat;

import static com.cpp.mscs.cricscore.controller.MatchController.INNINGS_TYPE;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/4/21
 * Time:  13:28
 */

@Service
public class InningsService {

    final
    InningsRepo inningsRepo;

    Logger LOGGER = LoggerFactory.getLogger(InningsService.class);

    @Autowired
    TeamRepo teamRepo;

    @Autowired
    MatchPlayedRepo matchPlayedRepo;

    @Autowired
    MatchService matchService;

    @Autowired
    MatchRepo matchRepo;

    public InningsService(InningsRepo inningsRepo) {
        this.inningsRepo = inningsRepo;
    }

    public void addInnings(Inning innings) throws IOException {

        LOGGER.info("Adding Innings details");
        try{
            inningsRepo.save(innings);
        }catch (Exception e){
            LOGGER.error("Failed to add Innings details {} ", innings);
        }

        LOGGER.info("Getting CityId of the Innings");
        long cityId = matchRepo.getCity(innings.getPrimaryKey().getMatchId());

        String matchSummaryData = matchService.getLiveMatchesIdsinTheCity(
                cityId).get(String.valueOf(innings.getPrimaryKey().getMatchId()));
        MatchSummary matchSummary = matchService.getMatchSummary(matchSummaryData);

        if(innings.getPrimaryKey().getInningtype().equalsIgnoreCase(INNINGS_TYPE[1])){
            matchSummary.setFirstInningsOver(true);
        }

        LOGGER.info("Add Match Summary Data");
        matchService.addMatchSummaryData(matchSummary, innings.getPrimaryKey().getMatchId()
                , (long) cityId);

    }

    @Transactional
    public void updatePlayersBattingInning(MatchPlayer matchPlayer, long matchId, String playerUuId) {
        LOGGER.info("Updating batting player {},  Mathc Id - {}", playerUuId, matchId);
        matchPlayer.setPrimaryKey(new ReferencePrimaryKey(matchId, playerUuId));
        try{
            matchPlayedRepo.updateBattingScore(matchPlayer.getBallsFaced(),
                    matchPlayer.getNumberOfFours(), matchPlayer.getNumberOfsixes(), matchPlayer.getOut(),matchPlayer.getPlayedPosition()
                    ,matchPlayer.getRun(), matchId, playerUuId);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Failed to Update Batting Player : {} ", e.getMessage()), e.fillInStackTrace());
        }

    }

    @Transactional
    public void updatePlayersBowlingInning(MatchPlayer matchPlayer, long matchId, String playerUuId) {
        matchPlayer.setPrimaryKey(new ReferencePrimaryKey(matchId, playerUuId));
        matchPlayedRepo.updateBowlingcore(matchPlayer.getExtra(),
                matchPlayer.getWicket(), matchPlayer.getOvers(), matchPlayer.getRunsGiven(), matchId, playerUuId);
    }
}