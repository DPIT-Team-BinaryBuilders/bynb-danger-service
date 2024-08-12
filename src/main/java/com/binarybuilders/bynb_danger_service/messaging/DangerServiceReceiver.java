package com.binarybuilders.bynb_danger_service.messaging;

import com.binarybuilders.bynb_danger_service.persistence.DangerEntity;
import com.binarybuilders.bynb_danger_service.repository.DangerRepository;
import com.binarybuilders.bynb_danger_service.service.DangerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DangerServiceReceiver {

    @Autowired
    private DangerService dangerService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DangerRepository dangerRepository;


    @RabbitListener(queues = RabbitMQConfig.USER_TO_DANGER_QUEUE)
    public void receiveMessage(String message) {
        try {
            // Convert the JSON string to a Danger object
            DangerEntity danger = objectMapper.readValue(message, DangerEntity.class);

            dangerRepository.save(danger);
            // Process the Danger object
            System.out.println("Received danger: " + danger);
            // Handle the danger creation
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
