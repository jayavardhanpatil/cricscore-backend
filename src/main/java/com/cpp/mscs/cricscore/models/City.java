package com.cpp.mscs.cricscore.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/4/21
 * Time:  11:48
 */

@Data
@Entity
@Table(name = "city")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class City {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "cityName")
    private String cityName;

    @Column(name = "state")
    private String state;

}