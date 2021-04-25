package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.City;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jmx.export.annotation.ManagedOperation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CityServiceTest {

    final static Trie trie = new Trie();

    @Mock
    CityService cityService;

    static {
        trie.insert(City.builder().cityId(13232L).cityName("Bangalore").state("KA").build());
        trie.insert(City.builder().cityId(1323232L).cityName("Banga").state("KA").build());
        trie.insert(City.builder().cityId(1332232L).cityName("Chnwew").state("KA").build());
        trie.insert(City.builder().cityId(1322332L).cityName("Ba").state("KA").build());
        trie.insert(City.builder().cityId(1322332L).cityName("Bangalo").state("KA").build());
    }

    @Test()
    void addCity() {
        trie.insert(City.builder().cityId(13232L).cityName("Los Angeles").state("KA").build());
        assertEquals(3, trie.children.size());
    }


    @Test
    void searchCity() {
        List<City> cities = (List<City>) trie.autoComplete("Ban");
        assertEquals(3, cities.size());
    }

    @Test
    void searchCityOnlyOne() {
        List<City> cities = (List<City>) trie.autoComplete("Bangalore");
        assertEquals(1, cities.size());
    }

    @Test
    void searchCityWhichIsnotAvaiable() {
        List<City> cities = (List<City>) trie.autoComplete("New York");
        assertEquals(0, cities.size());
    }
}