package com.cpp.mscs.cricscore.repositories;

import com.cpp.mscs.cricscore.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/3/21
 * Time:  13:57
 */

@Repository
public interface TeamRepo extends JpaRepository<Team, Long> {

    //@Query(value = "SELECT t.team_name, t.team_city from Team", nativeQuery = true)
    List<TeamNameAndCity> findByteamNameStartsWithIgnoreCase(String teamName);

    //@Query("Select t.teamId, t.teamName from Team t where t.teamId in (:teamIds)")
    TeamName findByTeamId(Long teamId);
}