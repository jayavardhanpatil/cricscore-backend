package com.cpp.mscs.cricscore.controller;

import com.cpp.mscs.cricscore.models.JedisModel;
import com.cpp.mscs.cricscore.services.CacheService;
import com.cpp.mscs.cricscore.services.JedisPoolHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 4/18/21
 * Time:  14:12
 */

@RestController
public class HealthCheckController {

    @Autowired
    CacheService cachingService;

    @GetMapping("/healthCheck")
    public ResponseEntity<?> getStatus(){
        return ResponseEntity.ok("Current time : "+ LocalDateTime.now());
    }

    @PostMapping("/addtoredis")
    public ResponseEntity<?> addKeyTORedis (@RequestBody JedisModel object){
        Jedis jedis = JedisPoolHelper.getResource();
        jedis.set(object.getKey(), object.getValue());
        return ResponseEntity.ok("added");
    }

    @GetMapping("/clearAllCaches")
    public void clearAllCaches() {
        cachingService.evictAllCaches();
    }
}