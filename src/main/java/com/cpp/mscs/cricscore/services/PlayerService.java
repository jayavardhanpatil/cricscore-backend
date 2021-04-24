package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.City;
import com.cpp.mscs.cricscore.models.Player;
import com.cpp.mscs.cricscore.repositories.PlayerRepo;
import com.cpp.mscs.cricscore.repositories.PlayersListForGivenCity;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PlayerRepo playerRepo;

    public Player getPlayer(String uuid){
        Optional<Player> player = playerRepo.findById(uuid);
        return player.orElse(null);
    }

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

    public List<PlayersListForGivenCity> getPlayersByCity(Long cityId){
        City city = new City();
        city.setCityId(cityId);
        return playerRepo.findByCity(city);
    }

    public List<Player> getPlayersByName(String playerName) {
        return playerRepo.findByNameStartsWith(playerName);
    }

//    public List<Player> getPlayersForaGivenCity(int cityId){
//
//    }
}