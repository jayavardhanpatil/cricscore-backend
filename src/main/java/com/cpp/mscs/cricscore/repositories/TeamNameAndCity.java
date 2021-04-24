package com.cpp.mscs.cricscore.repositories;

import com.cpp.mscs.cricscore.models.City;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/14/21
 * Time:  19:32
 */

public interface TeamNameAndCity {

    Long getTeamId();
    String getTeamName();
    City getTeamCity();
}
