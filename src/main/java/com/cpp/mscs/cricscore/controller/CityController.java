package com.cpp.mscs.cricscore.controller;

import com.cpp.mscs.cricscore.models.City;
import com.cpp.mscs.cricscore.services.CityService;
import com.cpp.mscs.cricscore.services.Trie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/4/21
 * Time:  13:26
 */

@RestController
public class CityController {

    @Autowired
    CityService cityService;

    Logger LOGGER = LoggerFactory.getLogger(CityController.class);

    @PostMapping("cities/add")
    public ResponseEntity<?> addCity(@RequestBody City city){
        LOGGER.info("Add City Request {}" , city);
        try{
            cityService.addCity(city);
            LOGGER.info("City added response : {} ", city);
            return ResponseEntity.ok().body(city);
        }catch (Exception e){
            LOGGER.error("Error :  {}" , e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("cities")
    public ResponseEntity<?> getAllCityList(){
        LOGGER.info("Request : City List");
        List<City> cities = cityService.getALlCities();
        LOGGER.info("Respose : Cities {} ", cities);
        return ResponseEntity.ok().body(cities);
    }

    @GetMapping("cities/find")
    public ResponseEntity<?> searchCity(@RequestParam String cityName){
        LOGGER.info("Request : search city : {}" , cityName);
        List<City> cities = cityService.searchCity(cityName);
        LOGGER.info("Response : cities {}", cities);
        return ResponseEntity.ok().body(cities);
    }
}