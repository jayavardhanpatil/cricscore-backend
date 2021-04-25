package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.CurrentPlaying;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.transaction.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/4/21
 * Time:  13:28
 */

@Service
public class CurrentPlayingPlayersService {

    ObjectMapper mapper = new ObjectMapper();

    Logger LOGGER = LoggerFactory.getLogger(CurrentPlayingPlayersService.class);

    @Autowired
    CurrentPlayingPlayersService currentPlayingPlayersService;

    @Autowired
    MatchService matchService;

    @Autowired
    InningsService inningsService;

    @Transactional
    public void saveCurrentPlayers(CurrentPlaying currentPlaying, long matchId, String inningType) throws JsonProcessingException {

        LOGGER.info("Saving current players");

        String key = matchId + "-" +inningType.toUpperCase();
        Jedis jedis = JedisPoolHelper.getResource();

        try{
            jedis.set(key, mapper.writeValueAsString(currentPlaying));
        }catch (Exception e){
            LOGGER.error("Failed to add Current players to the Redis");
        }

        LOGGER.info("Updating Players score data");

        currentPlaying.getBattingTeamPlayer().forEach(
                (k, v) -> inningsService.updatePlayersBattingInning(v, matchId, k)
        );

        currentPlaying.getBowlingTeamPlayer().forEach(
                (k, v) -> inningsService.updatePlayersBowlingInning(v, matchId, k)
        );

    }

    @Transactional
    public CurrentPlaying getCurrentPlayers(String key) {
        Jedis jedis = JedisPoolHelper.getResource();
        CurrentPlaying currentPlaying = null;
        LOGGER.info("Gettting current players from the redis");
        try {
            String redisKey = jedis.get(key);
            if(redisKey != null) {
                currentPlaying = mapper.readValue(redisKey, CurrentPlaying.class);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Json Exception could not parse the redis current player Object for the key {}, Exception : {}", key, e);
            e.printStackTrace();
        }
        return currentPlaying;
    }
}