package com.binarybuilders.bynb_danger_service.controller;

import com.binarybuilders.bynb_danger_service.dto.CreateDangerRequest;
import com.binarybuilders.bynb_danger_service.dto.GetAllDangerByUserLocationDto;
import com.binarybuilders.bynb_danger_service.dto.GetDangerRequest;
import com.binarybuilders.bynb_danger_service.dto.LocationDto;
import com.binarybuilders.bynb_danger_service.persistence.DangerEntity;
import com.binarybuilders.bynb_danger_service.service.DangerService;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/danger")
public class DangerController {
    DangerService dangerService;

    public DangerController(DangerService dangerService){
        this.dangerService = dangerService;
    }

    @GetMapping("/all")
    public List<GetDangerRequest> getAllDangers(){
        return dangerService.getAllDangers();
    }

    @PostMapping("/create")
    public void createDanger(@RequestBody CreateDangerRequest dangerCreateDto){
        dangerService.createDanger(dangerCreateDto);
    }
    @GetMapping("/get")
    public GetAllDangerByUserLocationDto getDanger(@RequestBody LocationDto userLocation){
        return dangerService.getDangerByUserLocation(userLocation);
    }
}
