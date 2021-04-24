package com.cpp.mscs.cricscore.repositories;

import com.cpp.mscs.cricscore.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/4/21
 * Time:  13:26
 */

public interface CityRepo extends JpaRepository<City, Long> {


    List<City> findAllByOrderByCityNameAsc();

    List<City> findByCityNameStartsWith(String cityName);

    List<City> findByCityNameStartsWithIgnoreCase(String cityName);
}
