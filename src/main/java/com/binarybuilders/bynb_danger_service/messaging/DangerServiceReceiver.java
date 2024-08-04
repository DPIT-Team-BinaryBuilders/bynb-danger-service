package com.binarybuilders.bynb_danger_service.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DangerServiceReceiver {
    @RabbitListener(queues = RabbitMQConfig.USER_TO_DANGER_QUEUE)
    public void receiveMessage(String message) {
        System.out.println("Received Message: " + message);
    }
}
