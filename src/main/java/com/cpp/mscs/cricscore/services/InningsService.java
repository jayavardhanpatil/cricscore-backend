package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.*;
import com.cpp.mscs.cricscore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

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
        inningsRepo.save(innings);

        long cityId = matchRepo.getCity(innings.getPrimaryKey().getMatchId());

        String matchSummaryData = matchService.getLiveMatchesIdsinTheCity(
                cityId).get(String.valueOf(innings.getPrimaryKey().getMatchId()));
        MatchSummary matchSummary = matchService.getMatchSummary(matchSummaryData);

        if(innings.getPrimaryKey().getInningtype().equalsIgnoreCase(INNINGS_TYPE[1])){
            matchSummary.setFirstInningsOver(true);
        }

        matchService.addMatchSummaryData(matchSummary, innings.getPrimaryKey().getMatchId()
                , (long) cityId);

    }

    @Transactional
    public void updatePlayersBattingInning(MatchPlayer matchPlayer, long matchId, String playerUuId) {
        matchPlayer.setPrimaryKey(new ReferencePrimaryKey(matchId, playerUuId));
        matchPlayedRepo.updateBattingScore(matchPlayer.getBallsFaced(),
                matchPlayer.getNumberOfFours(), matchPlayer.getNumberOfsixes(), matchPlayer.getOut(),matchPlayer.getPlayedPosition()
        ,matchPlayer.getRun(), matchId, playerUuId);
    }

    @Transactional
    public void updatePlayersBowlingInning(MatchPlayer matchPlayer, long matchId, String playerUuId) {
        matchPlayer.setPrimaryKey(new ReferencePrimaryKey(matchId, playerUuId));
        matchPlayedRepo.updateBowlingcore(matchPlayer.getExtra(),
                matchPlayer.getWicket(), matchPlayer.getOvers(), matchPlayer.getRunsGiven(), matchId, playerUuId);
    }
}