package com.cpp.mscs.cricscore.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 3/28/21
 * Time:  17:09
 */

@Data
@Entity
@Table(name = "match")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Match {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = " match_id")
    private Long matchId;

    @Column(name = "match_Venue", nullable = false)
    private int matchVenuecityId;

    @Column(name = "total_overs", nullable = false)
    private int totalOvers;

    @Column(name = "toss_won_team_id")
    private int tossWonTeamId;

    @Column(name = "selected_inning")
    private String selectedInning;

    @Column(name = "total_score")
    private int totalScore;

    @Column(name = "result")
    private String result;

    @Column(name = "won_team")
    private int winningTeamId;

    @Column(name = "teamA", nullable = false)
    private Integer teamAId;

    @Column(name = "teamB", nullable = false)
    private Integer teamBId;

    @Column(name = "match_date")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime matchDateTime;

    @Transient
    private List<String> teamAplayers;

    @Transient
    private List<String> teamBplayers;
}