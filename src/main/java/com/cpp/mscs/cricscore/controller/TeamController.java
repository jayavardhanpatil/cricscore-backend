package com.cpp.mscs.cricscore.controller;

import com.cpp.mscs.cricscore.models.Team;
import com.cpp.mscs.cricscore.services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/5/21
 * Time:  23:41
 */

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @PostMapping("teams/add")
    public ResponseEntity<?> addTeam(@RequestBody Team team){
        LOGGER.info("Adding Team Details {}", team);
        teamService.addTeam(team);
        return ResponseEntity.ok().body(team);
    }

    @PutMapping("teams/update")
    public ResponseEntity<?> updateTeam(@PathVariable Team team){
        return ResponseEntity.ok().body(teamService.updateTeam(team));
    }

    @GetMapping("teams/{teamId}")
    public ResponseEntity<?> getTeam(@PathVariable Long teamId){
        return ResponseEntity.ok().body(teamService.getTeam(teamId));
    }

    @GetMapping("teams/find")
    public ResponseEntity<?> findTeams(@RequestParam String teamName){
        System.out.println("Getting data");
        return ResponseEntity.ok().body(teamService.getTeamByName(teamName));
    }

    @GetMapping("teams/all")
    public ResponseEntity<?> getTeam() throws InterruptedException {
        return ResponseEntity.ok().body(teamService.getAllTeams());
    }
}