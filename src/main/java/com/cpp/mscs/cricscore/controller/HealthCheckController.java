package com.cpp.mscs.cricscore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 4/18/21
 * Time:  14:12
 */

@RestController
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public ResponseEntity<?> getStatus(){
        return ResponseEntity.ok(LocalDateTime.now());
    }
}