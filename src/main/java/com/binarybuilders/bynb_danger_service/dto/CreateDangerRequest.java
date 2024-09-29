package com.binarybuilders.bynb_danger_service.dto;

import com.binarybuilders.bynb_danger_service.persistence.GeoLocation;
import lombok.Getter;

import java.awt.*;

import java.sql.Array;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Getter
public class CreateDangerRequest {
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
    private String jwtToken;
}
