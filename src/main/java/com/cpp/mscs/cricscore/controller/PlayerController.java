package com.cpp.mscs.cricscore.controller;

import com.cpp.mscs.cricscore.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/4/21
 * Time:  18:17
 */

@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("players/all")
    public ResponseEntity<?> getALlPlayers(){
        return ResponseEntity.ok().body(playerService.getAllPlayer());
    }

    @GetMapping("players/city/{city_id}")
    public ResponseEntity<?> getALlPlayers(@PathVariable Long city_id){
        return ResponseEntity.ok().body(playerService.getPlayersByCity(city_id));
    }

    @GetMapping("players/find")
    public ResponseEntity<?> searchPlayer(@RequestParam String name){
        return ResponseEntity.ok().body(playerService.getPlayersByName(name));
    }

}