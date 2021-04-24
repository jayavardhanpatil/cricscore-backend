package com.cpp.mscs.cricscore.repositories;

import com.cpp.mscs.cricscore.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 3/28/21
 * Time:  18:18
 */

@Repository
public interface MatchRepo extends JpaRepository<Match, Long> {


    @Modifying(clearAutomatically = true)
    @Query("update Match m set m.result =:result, m.winningTeamId =:wonTeamId, m.totalScore =:target " +
            "where m.matchId =:matchId")
    void updateResult(@Param(value = "result") String result,
                      @Param(value = "wonTeamId") int wonTeamId,
                      @Param(value = "target") int target,
                      @Param(value = "matchId") long matchId);

    @Query("Select m.matchVenuecityId from Match m where m.matchId =:matchId")
    Long getCity(@Param("matchId") Long matchId);

}
