package com.cpp.mscs.cricscore.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 3/28/21
 * Time:  17:09
 */

@Data
@Table(name = "match_players")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchPlayer {

    @EmbeddedId
    private ReferencePrimaryKey primaryKey;

    @Column(name = "team_id")
    private Integer teamId;
    private Integer run;
    private Integer wicket;
    private Integer extra;
    private Double overs;
    private Integer ballsFaced;
    private Integer runsGiven;
    private Integer numberOfFours;
    private Integer numberOfsixes;
    private Integer playedPosition;
    private Boolean out;

    public MatchPlayer(){}

    public MatchPlayer(long matchId, String playerUuid, int teamId){
        ReferencePrimaryKey referencePrimaryKey = new ReferencePrimaryKey();
        referencePrimaryKey.setMatchId(matchId);
        referencePrimaryKey.setPlayeruuid(playerUuid);
        this.teamId = teamId;
        this.primaryKey = referencePrimaryKey;
    }

}
