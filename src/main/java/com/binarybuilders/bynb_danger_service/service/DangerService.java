package com.binarybuilders.bynb_danger_service.service;

import com.binarybuilders.bynb_danger_service.dto.DangerCreationDto;
import com.binarybuilders.bynb_danger_service.dto.GetAllDangerByUserLocationDto;
import com.binarybuilders.bynb_danger_service.dto.LocationDto;
import com.binarybuilders.bynb_danger_service.messaging.DangerServiceSender;
import com.binarybuilders.bynb_danger_service.persistence.DangerEntity;
import com.binarybuilders.bynb_danger_service.repository.DangerRepository;
import com.binarybuilders.bynb_danger_service.util.CalculateDistance;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Optional;

@Service
public class DangerService {

    private final DangerRepository dangerRepository;


    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private DangerServiceSender dangerServiceSender;
    @Autowired
    private HttpSecurity httpSecurity;

    public DangerService(DangerRepository dangerRepository) {
        this.dangerRepository = dangerRepository;

    }

    @Autowired
    private RabbitTemplate rabbitTemplate;


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

    public GetAllDangerByUserLocationDto getDangerByUserLocation(LocationDto userLocation) {
        double latitude = userLocation.getLatitude();
        double longitude = userLocation.getLongitude();
        double minDistance = 0.1; // 100 meters

        Optional<DangerEntity> closestDanger = Optional.empty();

        List<DangerEntity> dangers = dangerRepository.findAll();
        for (DangerEntity danger : dangers) {
            LocationDto dangerLocation = new LocationDto(danger.getDangerLocation().latitude, danger.getDangerLocation().longitude);
            double distance = CalculateDistance.calculateDistance(userLocation, dangerLocation);
            if (distance < minDistance) {
                minDistance = distance;
                closestDanger = Optional.of(danger);
            }
        }

        if (closestDanger.isPresent()) {
            GetAllDangerByUserLocationDto getAllDangerByUserLocationDto = new GetAllDangerByUserLocationDto();
            getAllDangerByUserLocationDto.setDangerName(closestDanger.get().getName());
            getAllDangerByUserLocationDto.setLatitude(closestDanger.get().getDangerLocation().getLatitude());
            getAllDangerByUserLocationDto.setLongitude(closestDanger.get().getDangerLocation().getLongitude());
            return getAllDangerByUserLocationDto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No danger found");
        }
    }
}


