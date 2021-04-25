package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.City;
import com.cpp.mscs.cricscore.models.Player;
import com.cpp.mscs.cricscore.repositories.PlayerRepo;
import com.cpp.mscs.cricscore.repositories.PlayersListForGivenCity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/2/21
 * Time:  13:42
 */

@Service
public class PlayerService {

    Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    PlayerRepo playerRepo;

    @Cacheable("player")
    public Player getPlayer(String uuid){
        Optional<Player> player = playerRepo.findById(uuid);
        return player.orElse(null);
    }

    @Cacheable("players")
    public List<Player> getAllPlayer(){
         return playerRepo.findAll();
    }

//    public List<Player> getPlayersByCity(int cityId){
//        return playerRepo.findPlayerByCityId(int cittyId);
//    }

    public boolean addPlayer(Player player){
        try {
            playerRepo.save(player);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Cacheable("playersInCity")
    public List<PlayersListForGivenCity> getPlayersByCity(Long cityId){
        City city = new City();
        city.setCityId(cityId);
        return playerRepo.findByCity(city);
    }

    @Cacheable("playersByName")
    public List<Player> getPlayersByName(String playerName) {
        return playerRepo.findByNameStartsWith(playerName);
    }

//    public List<Player> getPlayersForaGivenCity(int cityId){
//
//    }
}