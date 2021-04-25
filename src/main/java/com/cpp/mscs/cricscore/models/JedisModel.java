package com.cpp.mscs.cricscore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 4/23/21
 * Time:  22:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JedisModel {

    private String key;

    private String value;


}