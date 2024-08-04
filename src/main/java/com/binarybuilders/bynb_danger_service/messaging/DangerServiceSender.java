package com.binarybuilders.bynb_danger_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DangerServiceSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public DangerServiceSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_DANGER_TO_USER, message);
        System.out.println("Sent message: " + message);
    }
}
