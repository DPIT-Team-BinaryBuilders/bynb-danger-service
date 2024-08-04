package com.binarybuilders.bynb_danger_service.messaging;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "user_danger_exchange";
    public static final String USER_TO_DANGER_QUEUE = "user_to_danger_queue";
    public static final String DANGER_TO_USER_QUEUE = "danger_to_user_queue";
    public static final String ROUTING_KEY_USER_TO_DANGER = "user_to_danger";
    public static final String ROUTING_KEY_DANGER_TO_USER = "danger_to_user";


    @Bean
    Queue userToDangerQueue() {
        return new Queue(USER_TO_DANGER_QUEUE, true);
    }

    @Bean
    Queue dangerToUserQueue() {
        return new Queue(DANGER_TO_USER_QUEUE, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding bindingUserToDanger(Queue userToDangerQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userToDangerQueue).to(exchange).with(ROUTING_KEY_USER_TO_DANGER);
    }

    @Bean
    Binding bindingDangerToUser(Queue dangerToUserQueue, TopicExchange exchange) {
        return BindingBuilder.bind(dangerToUserQueue).to(exchange).with(ROUTING_KEY_DANGER_TO_USER);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

}