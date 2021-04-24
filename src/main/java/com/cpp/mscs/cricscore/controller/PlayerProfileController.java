package com.cpp.mscs.cricscore.controller;

import com.cpp.mscs.cricscore.models.Player;
import com.cpp.mscs.cricscore.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/2/21
 * Time:  11:51
 */

@RestController
public class PlayerProfileController {

    @Autowired
    PlayerService playerService;

    @PostMapping("players/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player){
        System.out.println(player);
        playerService.addPlayer(player);
        return ResponseEntity.ok().body(player);
    }

    @PutMapping("players/{playerId}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player playerId){
        playerService.addPlayer(playerId);
        return ResponseEntity.ok(playerId);
    }

    @GetMapping("players/{playerId}")
    public Player getPlayer(@PathVariable String playerId){
        return playerService.getPlayer(playerId);
    }

//    @GetMapping("player")
//    public ResponseEntity<?> getAllPlayer(){
//        return ResponseEntity.ok().body(playerService.getAllPlayer());
//    }


}