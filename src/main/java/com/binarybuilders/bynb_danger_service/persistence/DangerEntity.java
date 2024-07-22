package com.binarybuilders.bynb_danger_service.persistence;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_danger")
@Setter
@Getter
public class DangerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "accuracy")
    private double accuracy;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private DangerType type;

    // good practice: folosim string in loc de timestamp si apoi facem conversie
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime timeCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime duration;

    @Column(name = "user_id")
    private Long userId;

    // danger location
    /*
      - to be added danger_location column to the danger database
      - [danger_location] should have a Geographic Value, containing longitute and latitude coordinates
      * creez obiect spearat (double) lat, lng (jsonb)
     */

    @Column(columnDefinition = "jsonb", nullable = false, updatable = true, name = "danger_location")
    @Type(JsonBinaryType.class)
    private GeoLocation dangerLocation;

    @Column(name = "danger_level")
    private Double dangerLevel;

    @Column(name = "description", length = 120)
    private String description;

    @Column(name = "additional_information", length = 120)
    private String additionalInformation;
}
