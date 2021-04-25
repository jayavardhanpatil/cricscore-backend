package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.Team;
import com.cpp.mscs.cricscore.repositories.TeamRepo;
import com.cpp.mscs.cricscore.repositories.TeamNameAndCity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/5/21
 * Time:  23:44
 */

@Service
public class TeamService {


    @Autowired
    TeamRepo teamRepo;

    Logger LOGGER = LoggerFactory.getLogger(TeamService.class);

    @Transactional
    public Long addTeam(Team team){
        LOGGER.info("Adding Team : {}", team);
        Long teamId = teamRepo.save(team).getTeamId();
        LOGGER.info("Sucessfully Added team {} - team Id {}", teamId, team );
        return teamId;
    }

    @Cacheable("teamById")
    public Optional<Team> getTeam(Long teamId){

        LOGGER.info("Getting Team Id - {}", teamId);
        return teamRepo.findById(teamId);
    }

    @Cacheable("getAllTeams")
    public List<Team> getAllTeams() {
        LOGGER.info("Getting All Teams - ");
        return teamRepo.findAll();
    }

    @Cacheable("getTeamsByName")
    public List<TeamNameAndCity> getTeamByName(String teamName) {
        List<TeamNameAndCity> respone = teamRepo.findByteamNameStartsWithIgnoreCase(teamName);
        System.out.println(respone);
        return respone;
    }

    @Transactional
    public boolean updateTeam(Team team) {
        LOGGER.info("Updating Team {}", team);
        try {
            teamRepo.save(team);
            return true;
        }catch (Exception e){
            LOGGER.error("Failed to update Team");
            return false;
        }
    }
}