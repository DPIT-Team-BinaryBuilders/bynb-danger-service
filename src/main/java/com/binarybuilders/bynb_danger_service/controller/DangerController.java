package com.binarybuilders.bynb_danger_service.controller;

import com.binarybuilders.bynb_danger_service.dto.DangerCreationDto;
import com.binarybuilders.bynb_danger_service.dto.GetAllDangerByUserLocationDto;
import com.binarybuilders.bynb_danger_service.dto.LocationDto;
import com.binarybuilders.bynb_danger_service.persistence.DangerEntity;
import com.binarybuilders.bynb_danger_service.service.DangerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/danger")
public class DangerController {
    DangerService dangerService;

    public DangerController(DangerService dangerService){
        this.dangerService = dangerService;
    }


    @PostMapping("/create-danger")
    public void createDanger(@RequestBody DangerCreationDto dangerCreateDto){
        dangerService.createDanger(dangerCreateDto);
    }

    @GetMapping("/get")
    public GetAllDangerByUserLocationDto getDanger(@RequestBody LocationDto userLocation){
        return dangerService.getDangerByUserLocation(userLocation);
    }
}
