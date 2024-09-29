package com.binarybuilders.bynb_danger_service.dto;

import com.binarybuilders.bynb_danger_service.persistence.GeoLocation;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class GetDangerRequest {
    private String name;
    private double accuracy;
    private String type;
    private ZonedDateTime timeCreated;
    private ZonedDateTime duration;
    private GeoLocation dangerLocation;
    private Double dangerLevel;
    private String description;
    private String additionalInformation;
    private GeoLocation[] rectanglePoints;
    private Long userId;
    private Long id;
}
