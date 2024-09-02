package com.binarybuilders.bynb_danger_service.dto;

import com.binarybuilders.bynb_danger_service.persistence.DangerType;
import com.binarybuilders.bynb_danger_service.persistence.GeoLocation;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class DangerCreationDto {
    private String name;
    private double accuracy;
    private DangerType type;
    private ZonedDateTime timeCreated;
    private ZonedDateTime duration;
    private GeoLocation dangerLocation;
    private Double dangerLevel;
    private String description;
    private String additionalInformation;
    private String jwt;
}
