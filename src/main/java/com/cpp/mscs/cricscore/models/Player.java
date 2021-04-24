package com.cpp.mscs.cricscore.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 2/2/21
 * Time:  11:44
 */

@Data
@Entity
@Table(name = "player")
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "Fk_cityId")
    private City city;

    @Column(name = "phNumber")
    private Long phoneNumber;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(name = "profilePath")
    private String photoUrl;

    @Column(name = "email")
    private String email;

}