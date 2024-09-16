package com.binarybuilders.bynb_danger_service.dto;

import com.binarybuilders.bynb_danger_service.persistence.DangerType;
import com.binarybuilders.bynb_danger_service.persistence.GeoLocation;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
public class LocationDto {
    private double latitude;
    private double longitude;

    public LocationDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}