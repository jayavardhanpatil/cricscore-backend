package com.cpp.mscs.cricscore.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 3/29/21
 * Time:  21:22
 */


@Data
@Entity
@Table(name = "inning")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Inning implements Serializable {

    @EmbeddedId
    private ReferencePrimaryKeyInningTable primaryKey;

    private Integer battingTeamId;

    private Integer bowlingTeamId;

    private Integer run;

    private Integer wickets;

    private double overs;

    private Integer extra;

    public Inning(){}

    public Inning(long matchId, String inningtype){
        ReferencePrimaryKeyInningTable referencePrimaryKey = new ReferencePrimaryKeyInningTable();
        referencePrimaryKey.setMatchId(matchId);
        referencePrimaryKey.setInningtype(inningtype);
        this.primaryKey = referencePrimaryKey;
    }
}