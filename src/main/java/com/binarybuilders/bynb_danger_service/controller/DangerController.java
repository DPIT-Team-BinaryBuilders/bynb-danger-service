package com.binarybuilders.bynb_danger_service.controller;

import com.binarybuilders.bynb_danger_service.dto.DangerCreationDto;
import com.binarybuilders.bynb_danger_service.service.DangerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
