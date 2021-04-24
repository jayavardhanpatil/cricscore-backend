package com.cpp.mscs.cricscore.repositories;

import com.cpp.mscs.cricscore.models.Inning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 3/28/21
 * Time:  18:18
 */

@Repository
public interface InningsRepo extends JpaRepository<Inning, Long> {


}
