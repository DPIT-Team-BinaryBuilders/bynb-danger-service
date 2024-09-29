package com.binarybuilders.bynb_danger_service.persistence;

import lombok.Getter;

@Getter
public class GeoLocation {
    public double lat;
    public double lng;

    public GeoLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
