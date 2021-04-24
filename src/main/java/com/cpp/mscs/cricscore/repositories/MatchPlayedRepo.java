package com.cpp.mscs.cricscore.repositories;

import com.cpp.mscs.cricscore.models.MatchPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 3/28/21
 * Time:  18:18
 */

@Repository
public interface MatchPlayedRepo extends JpaRepository<MatchPlayer, Long> {


    @Modifying(clearAutomatically = true)
    @Query("update MatchPlayer p set p.ballsFaced =:ballsFaced, p.numberOfFours =:numberOfFours, " +
            "p.numberOfsixes =:numberOfSixes, p.out =:out, p.playedPosition =:playedPosiiton, " +
            "p.run =:runs where p.primaryKey.matchId =:matchId and p.primaryKey.playeruuid =:uuId")
    public void updateBattingScore(@Param(value = "ballsFaced") int ballsFaced,
                                   @Param(value = "numberOfFours") int numberOfFours,
                                   @Param(value = "numberOfSixes") int numberOfSixes,
                                   @Param(value = "out") boolean out,
                                   @Param(value = "playedPosiiton") int playedPosiiton,
                                   @Param(value = "runs") int runs,
                                   @Param(value = "matchId") long matchId,
                                   @Param(value = "uuId") String uuId
    );

    @Modifying(clearAutomatically = true)
    @Query("update MatchPlayer p set p.extra =:extra, p.wicket =:wickets, " +
            "p.overs =:overs, p.runsGiven =:runsGiven where p.primaryKey.matchId =:matchId and p.primaryKey.playeruuid =:uuId")
    public void updateBowlingcore(@Param(value = "extra") int extra,
                                  @Param(value = "wickets") int wickets,
                                  @Param(value = "overs") double overs,
                                  @Param(value = "runsGiven") int runsGiven,
                                  @Param(value = "matchId") long matchId,
                                  @Param(value = "uuId") String uuId
    );




    @Query(
            value = "SELECT * FROM match_players m WHERE m.match_id = ?1",
            nativeQuery = true)
    List<MatchPlayer> findAllByMatchId(Long matchId);
}
