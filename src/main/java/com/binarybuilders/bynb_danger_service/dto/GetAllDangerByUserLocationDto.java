package com.binarybuilders.bynb_danger_service.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllDangerByUserLocationDto {
    private int id;
    private String dangerName;
    private double lat;
    private double lng;

}