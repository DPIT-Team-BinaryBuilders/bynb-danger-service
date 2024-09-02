package com.binarybuilders.bynb_danger_service.service;

import com.binarybuilders.bynb_danger_service.dto.DangerCreationDto;
import com.binarybuilders.bynb_danger_service.messaging.DangerServiceSender;
import com.binarybuilders.bynb_danger_service.persistence.DangerEntity;
import com.binarybuilders.bynb_danger_service.repository.DangerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;

@Service
public class DangerService {

    private final DangerRepository dangerRepository;


    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private DangerServiceSender dangerServiceSender;

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
        String jwt = dangerCreateDto.getJwt();
        Claims claims = Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();
        String username = claims.getSubject();
        String userId =  dangerServiceSender.getUserIdFromUsername(username);
        danger.setUserId(Long.parseLong(userId));
        dangerRepository.save(danger);
    }


    private SecretKey generateKey(){
        byte[] secreateAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secreateAsBytes);
    }

    }


