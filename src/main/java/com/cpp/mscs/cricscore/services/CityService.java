package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.City;
import com.cpp.mscs.cricscore.repositories.CityRepo;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/4/21
 * Time:  13:28
 */

@Service
public class CityService {

    @Autowired
    CityRepo cityRepo;

    final Trie trie = new Trie();

    public static List<City> cities = new ArrayList<>();

    Logger LOGGER = LoggerFactory.getLogger(CityService.class);

    public void addCity(City city){
        LOGGER.info("Adding city - {} to DB ",city);
        try{
            cityRepo.save(city);
        }catch (Exception e){
            LOGGER.error("Failed to Add city : {}", city);
        }
    }

    @Cacheable("cities")
    public List<City> getALlCities(){
        LOGGER.info("getting All cities - from DB ");
        if(cities.size() == 0){
            cities = cityRepo.findAllByOrderByCityNameAsc();
            for(City city : cities){
                if(city != null)
                    trie.insert(city);
            }
        }
        return cities;
    }

    public List<City> searchCity(String cityName){
        LOGGER.info("Searching for the city starts with - {} : "+cityName);
        if(cities.size() == 0){
            cities = getALlCities();
        }
        return (List<City>) trie.autoComplete(cityName);
        //return cityRepo.findByCityNameStartsWithIgnoreCase(cityName);
    }

}