package com.cpp.mscs.cricscore.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 4/17/21
 * Time:  12:11
 */

@Data
@AllArgsConstructor(staticName = "of")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerScoreCard {
    @JsonValue
    Map<String, MatchPlayer> playerNameAndScoreMap;

    @JsonCreator
    public PlayerScoreCard(){
        this.playerNameAndScoreMap = new HashMap<>();
    }
}