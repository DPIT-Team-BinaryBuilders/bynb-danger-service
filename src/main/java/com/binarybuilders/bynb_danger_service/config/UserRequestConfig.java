//package com.binarybuilders.bynb_danger_service.config;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class UserRequestConfig {
//    public static final String REQUEST_QUEUE = "user.request.queue";
//    public static final String REPLY_QUEUE = "user.reply.queue";
//
//    @Bean
//    public Queue userRequestQueue() {
//        return new Queue(REQUEST_QUEUE);
//    }
//
//    @Bean
//    public Queue userReplyQueue() {
//        return new Queue(REPLY_QUEUE);
//    }
//}
