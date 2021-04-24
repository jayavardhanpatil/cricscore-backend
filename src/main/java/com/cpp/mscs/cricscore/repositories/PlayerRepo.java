package com.cpp.mscs.cricscore.repositories;

import com.cpp.mscs.cricscore.models.City;
import com.cpp.mscs.cricscore.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/2/21
 * Time:  11:56
 */
@Repository
public interface PlayerRepo extends JpaRepository<Player, String> {


    //@Query(value = "Select p from player p where p.fk_city_id = ?", nativeQuery = true)
    List<PlayersListForGivenCity> findByCity(City cityId);

    List<Player> findByNameStartsWith(String playerName);
}