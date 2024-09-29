package com.binarybuilders.bynb_danger_service.dto;
import com.binarybuilders.bynb_danger_service.persistence.DangerType;
import com.binarybuilders.bynb_danger_service.persistence.GeoLocation;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
public class LocationDto {
    private double lat;
    private double lng;

    public LocationDto(double latitude, double longitude) {
        this.lat = latitude;
        this.lng = longitude;
    }
}