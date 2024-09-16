package com.binarybuilders.bynb_danger_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue userLocationQueue() {
        return new Queue("user.location.queue", false);
    }

    @Bean
    public Queue dangerLocationQueue() {
        return new Queue("danger.location.queue", false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("location.exchange");
    }

    @Bean
    public Binding userLocationBinding(Queue userLocationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userLocationQueue).to(exchange).with("user.location");
    }

    @Bean
    public Binding dangerLocationBinding(Queue dangerLocationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(dangerLocationQueue).to(exchange).with("danger.location");
    }
}

