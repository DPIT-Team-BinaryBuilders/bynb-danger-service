package com.binarybuilders.bynb_danger_service.service;

import com.binarybuilders.bynb_danger_service.dto.CreateDangerRequest;
import com.binarybuilders.bynb_danger_service.dto.GetAllDangerByUserLocationDto;
import com.binarybuilders.bynb_danger_service.dto.GetDangerRequest;
import com.binarybuilders.bynb_danger_service.dto.LocationDto;
import com.binarybuilders.bynb_danger_service.messaging.DangerServiceSender;
import com.binarybuilders.bynb_danger_service.persistence.DangerEntity;
import com.binarybuilders.bynb_danger_service.persistence.GeoLocation;
import com.binarybuilders.bynb_danger_service.repository.DangerRepository;
import com.binarybuilders.bynb_danger_service.util.CalculateDistance;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public List<GetDangerRequest> getAllDangers() {
        List<DangerEntity> dangerEntities = dangerRepository.findAll();
        return dangerEntities.stream().map(dangerEntity -> {
            GetDangerRequest danger = new GetDangerRequest();
            danger.setAccuracy(dangerEntity.getAccuracy());
            danger.setType(dangerEntity.getType());
            danger.setDescription(dangerEntity.getDescription());
            danger.setDangerLocation(dangerEntity.getDangerLocation());
            danger.setTimeCreated(dangerEntity.getTimeCreated());
            danger.setAdditionalInformation(dangerEntity.getAdditionalInformation());
            danger.setName(dangerEntity.getName());
            danger.setDuration(dangerEntity.getDuration());
            danger.setDangerLevel(dangerEntity.getDangerLevel());
            if(!Objects.equals(dangerEntity.getRectanglePoints(), "")){
                String[] rectanglePoints = dangerEntity.getRectanglePoints().split(" ");
                GeoLocation[] geoLocations = new GeoLocation[4];
                for(int i = 0; i <= 3; i++){
                    geoLocations[i] = new GeoLocation(Double.parseDouble(rectanglePoints[i*2]), Double.parseDouble(rectanglePoints[i*2+1]));
                }
                System.out.println(geoLocations);
                danger.setRectanglePoints(geoLocations);
            }
            else
                danger.setRectanglePoints(null);
            danger.setUserId(dangerEntity.getUserId());
            danger.setId(dangerEntity.getId());
            return danger;
        }).toList();
    }

    public void createDanger(CreateDangerRequest dangerCreateDto) {
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
        StringBuilder rectanglePoints= new StringBuilder();
        if(dangerCreateDto.getRectanglePoints().length!=0){
            for(int i = 0 ; i <= 3; i++){
                rectanglePoints.append(dangerCreateDto.getRectanglePoints()[i].lat);
                rectanglePoints.append(" ");
                rectanglePoints.append(dangerCreateDto.getRectanglePoints()[i].lng);
                rectanglePoints.append(" ");
            }
        }
        else{
            danger.setRectanglePoints(null);
        }


        danger.setRectanglePoints(rectanglePoints.toString());
        System.out.println(danger.getRectanglePoints());
        String jwt = dangerCreateDto.getJwtToken();
        System.out.println(jwt);
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
        double latitude = userLocation.getLat();
        double longitude = userLocation.getLng();
        double minDistance = 0.1; // 100 meters

        Optional<DangerEntity> closestDanger = Optional.empty();

        List<DangerEntity> dangers = dangerRepository.findAll();
        for (DangerEntity danger : dangers) {
            LocationDto dangerLocation = new LocationDto(danger.getDangerLocation().getLat(), danger.getDangerLocation().getLng());
            double distance = CalculateDistance.calculateDistance(userLocation, dangerLocation);
            if (distance < minDistance) {
                minDistance = distance;
                closestDanger = Optional.of(danger);
            }
        }

        if (closestDanger.isPresent()) {
            GetAllDangerByUserLocationDto getAllDangerByUserLocationDto = new GetAllDangerByUserLocationDto();
            getAllDangerByUserLocationDto.setDangerName(closestDanger.get().getName());
            getAllDangerByUserLocationDto.setLat(closestDanger.get().getDangerLocation().getLat());
            getAllDangerByUserLocationDto.setLng(closestDanger.get().getDangerLocation().getLng());
            getAllDangerByUserLocationDto.setId(closestDanger.get().getId().intValue());
            return getAllDangerByUserLocationDto;
        } else {
            return null;
        }
    }
}


