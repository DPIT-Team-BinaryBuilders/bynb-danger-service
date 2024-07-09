package com.binarybuilders.bynb_danger_service.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.*;
import java.util.Date;

@Entity
@Table(name = "DANGERS")
@Setter
@Getter
public class Danger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "accuracy")
    private double accuracy;

    @Column(name = "type")
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date duration;

    // user id
    /*
      - to be added user_id column to the danger database
      - [user_id] should have a ManyToOne relationship with the [id] from user database
     */

    // danger location
    /*
      - to be added danger_location column to the danger database
      - [danger_location] should have a Geographic Value, containing longitute and latitude coordinates
      - it will have a OneToMany relationship with the [location] from the map database
     */

    @Column(name = "danger_level")
    private Double dangerLevel;

    @Column(name = "description", length = 120)
    private String description;

    @Column(name = "additional_information", length = 120)
    private String additionalInformation;
}
