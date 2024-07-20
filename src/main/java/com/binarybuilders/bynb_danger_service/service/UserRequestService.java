//package com.binarybuilders.bynb_danger_service.service;
//
//import com.binarybuilders.bynb_danger_service.config.UserRequestConfig;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserRequestService {
//
//    private final RabbitTemplate rabbitTemplate;
//
//    public UserRequestService(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public String getUserId(String dangerId) {
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setReplyTo(UserRequestConfig.REPLY_QUEUE);
//        Message message = new Message(dangerId.getBytes(), messageProperties);
//
//        Message response = rabbitTemplate.sendAndReceive(UserRequestConfig.REQUEST_QUEUE, message);
//        return response != null ? new String(response.getBody()) : null;
//    }
//}
