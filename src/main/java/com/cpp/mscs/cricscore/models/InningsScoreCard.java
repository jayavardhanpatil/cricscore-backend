package com.cpp.mscs.cricscore.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 4/17/21
 * Time:  12:10
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InningsScoreCard {

    private PlayerScoreCard battingplayerScoreCard;

    private PlayerScoreCard bowlingPlayerScoreCard;

    public InningsScoreCard(){
        this.battingplayerScoreCard = new PlayerScoreCard();
        this.bowlingPlayerScoreCard = new PlayerScoreCard();
    }

}