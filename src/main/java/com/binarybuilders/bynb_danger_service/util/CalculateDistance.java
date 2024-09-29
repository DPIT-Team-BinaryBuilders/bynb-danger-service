package com.binarybuilders.bynb_danger_service.util;

import com.binarybuilders.bynb_danger_service.dto.LocationDto;

public class CalculateDistance {
    private static final int EARTH_RADIUS_KM = 6371;  // Earth radius in kilometers

    public static double calculateDistance(LocationDto userLocation, LocationDto dangerLocation) {
        double latDistance = Math.toRadians(dangerLocation.getLat() - userLocation.getLat());
        double lonDistance = Math.toRadians(dangerLocation.getLng() - userLocation.getLng());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLocation.getLng())) * Math.cos(Math.toRadians(dangerLocation.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}