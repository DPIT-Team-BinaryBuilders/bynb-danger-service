package com.binarybuilders.bynb_danger_service.messaging;

import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DangerServiceSender {


    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(DangerServiceSender.class);
    public String getUserIdFromUsername(String username) {


        // Send the message to the user.request.queue
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_DANGER_TO_USER, username);

        // Receive the response from user.response.queue
        Message response = rabbitTemplate.receive(RabbitMQConfig.USER_TO_DANGER_QUEUE, 5000); // Timeout after 5 seconds

        if (response != null) {
            String userId = new String(response.getBody());
            System.out.println("Received userId: " + userId);
            return userId;
        } else {
            throw new RuntimeException("Failed to get userId from User service.");
        }
    }
}
