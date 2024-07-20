//package com.binarybuilders.bynb_danger_service.config;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DangerRequestConfig {
//    public static final String REQUEST_QUEUE = "danger.request.queue";
//    public static final String REPLY_QUEUE = "danger.reply.queue";
//
//    @Bean
//    public Queue requestQueue() {
//        return new Queue(REQUEST_QUEUE);
//    }
//
//    @Bean
//    public Queue replyQueue() {
//        return new Queue(REPLY_QUEUE);
//    }
//}
