package com.binarybuilders.bynb_danger_service.service;

import com.binarybuilders.bynb_danger_service.dto.DangerCreationDto;
import com.binarybuilders.bynb_danger_service.persistence.DangerEntity;
import com.binarybuilders.bynb_danger_service.repository.DangerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DangerService {

    private final DangerRepository dangerRepository;

    @Autowired
    public DangerService(DangerRepository dangerRepository) {
        this.dangerRepository = dangerRepository;
    }

    public DangerEntity saveDanger(DangerEntity danger) {
        return dangerRepository.save(danger);
    }

    public DangerEntity getDangerById(Long id) {
        return dangerRepository.findById(id).orElse(null);
    }

    public List<DangerEntity> getAllUserDangers(Long id) {
        return dangerRepository.findByUserId(id);
    }

    public void deleteDanger(Long id) {
        dangerRepository.deleteById(id);
    }

    public List<DangerEntity> getAllDangers() {
        return dangerRepository.findAll();
    }

    public void createDanger(DangerCreationDto dangerCreateDto) {
        DangerEntity danger = new DangerEntity();
        danger.setAccuracy(dangerCreateDto.getAccuracy());
        danger.setType(dangerCreateDto.getType());
        danger.setDescription(dangerCreateDto.getDescription());
        danger.setDangerLocation(dangerCreateDto.getDangerLocation());
        danger.setTimeCreated(dangerCreateDto.getTimeCreated());
        danger.setAdditionalInformation(dangerCreateDto.getAdditionalInformation());
        danger.setName(dangerCreateDto.getName());
        danger.setDuration(dangerCreateDto.getDuration());
        danger.setDangerLevel(dangerCreateDto.getDangerLevel());
        String userId = "111";
        danger.setUserId(Long.parseLong(userId));
        dangerRepository.save(danger);
    }
}