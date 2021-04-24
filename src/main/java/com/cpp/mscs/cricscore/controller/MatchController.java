package com.cpp.mscs.cricscore.controller;

import com.cpp.mscs.cricscore.models.*;
import com.cpp.mscs.cricscore.services.CurrentPlayingPlayersService;
import com.cpp.mscs.cricscore.services.InningsService;
import com.cpp.mscs.cricscore.services.MatchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 3/28/21
 * Time:  18:17
 */

@Controller
public class MatchController {


    public static String[] INNINGS_TYPE = {"FIRST", "SECOND"};

    @Autowired
    MatchService matchService;

    @Autowired
    InningsService inningsService;

    @Autowired
    CurrentPlayingPlayersService currentPlayingPlayersService;

    @PostMapping("match/add")
    public ResponseEntity<?> addMatch(@RequestBody Match match){
        matchService.addMatch(match);
        return ResponseEntity.ok().body(match);
    }

    @GetMapping("match/{matchId}")
    public ResponseEntity<?> getMatch(@PathVariable Long matchId){
        return ResponseEntity.ok().body(matchService.getMatch(matchId));
    }

    @PutMapping("match/{matchId}")
    public ResponseEntity<?> updateMatchResult(@PathVariable Long matchId, @RequestBody Match match) throws IOException {
        matchService.updateMatch(matchId, match);
        return ResponseEntity.ok().body(match);
    }

    @PostMapping("match/{matchId}/innings/{inningsType}/add")
    public ResponseEntity<?> addInnings(@PathVariable Long matchId,
                                        @PathVariable String inningsType,
                                        @RequestBody Inning innings) throws IOException {
        innings.setPrimaryKey(new ReferencePrimaryKeyInningTable(matchId, inningsType));
        inningsService.addInnings(innings);
        return ResponseEntity.ok().body(innings);
    }

    @PostMapping("match/{matchId}/innings/{inningType}/current-players/add")
    public ResponseEntity<?> addCurrentPlayers(@PathVariable Long matchId,
                                               @PathVariable String inningType,
                                               @RequestBody CurrentPlaying currentPlaying){
        try {
            currentPlayingPlayersService.saveCurrentPlayers(currentPlaying, matchId, inningType);
            return ResponseEntity.ok().body(currentPlaying);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e);
        }
    }

    @PostMapping("match/{matchId}/{cityId}/match-summary")
    public ResponseEntity<?> addCurrentPlayers(@PathVariable Long matchId,
                                               @PathVariable Long cityId,
                                               @RequestBody MatchSummary matchSummary) throws IOException {
        matchService.addMatchSummaryData(matchSummary, matchId, cityId);
        return ResponseEntity.ok().body(matchSummary);
    }

//    @GetMapping("match/{matchId}/innings/{inningType}/current-players")
//    public ResponseEntity<?> addCurrentPlayers(@PathVariable Long matchId,
//                                               @PathVariable String inningType){
//        try {
//            String key = matchId + "-" +inningType.toUpperCase();
//            return ResponseEntity.ok().body(currentPlayingPlayersService.getCurrentPlayers(key));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body(e);
//        }
//    }

    @PutMapping("match/{matchId}/players/{playeruuid}/updateBattingScore")
    public ResponseEntity<?> updateBattingScoreInning(@PathVariable Long matchId,
                                                      @PathVariable String playeruuid,
                                                      @RequestBody MatchPlayer player){
        try {
            inningsService.updatePlayersBattingInning(player, matchId, playeruuid);
            return ResponseEntity.ok().body(player);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e);
        }
    }

    @PutMapping("match/{matchId}/players/{playeruuid}/updateBowlingScore")
    public ResponseEntity<?> updateBowlingScore(@PathVariable Long matchId,
                                                @PathVariable String playeruuid,
                                                @RequestBody MatchPlayer player){
        try {
            inningsService.updatePlayersBowlingInning(player, matchId, playeruuid);
            return ResponseEntity.ok().body(player);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e);
        }
    }

    @GetMapping("/matches/cities/{citiId}/")
    public ResponseEntity<?> getMatchesInyourCity(@PathVariable Long citiId){
        List<MatchSummary> matchSummary = matchService.getMatchesinTheCity(citiId);
        return ResponseEntity.ok(matchSummary);
    }

    @GetMapping("/matches/{matchId}")
    public ResponseEntity<?> getInningsPlayerScore(@PathVariable Long matchId,
                                                   @RequestHeader("battingTeamId") Long battingTeamId){
        InningsScoreCard inningsScoreCard = matchService.getInningsScoreCard(matchId, battingTeamId);
        return ResponseEntity.ok(inningsScoreCard);
    }
}