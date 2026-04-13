package com.SOA.DeliveryService;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("order.exchange");
    }

    @Bean
    public Queue deliveryRequestQueue() {
        return new Queue("delivery.request.queue", true);
    }

    @Bean
    public Queue deliveryAssignedQueue() {
        return new Queue("delivery.assigned.queue", true);
    }

    @Bean
    public Binding deliveryRequestBinding(Queue deliveryRequestQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deliveryRequestQueue)
                .to(exchange)
                .with("delivery.request");
    }

    @Bean
    public Binding deliveryAssignedBinding(Queue deliveryAssignedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deliveryAssignedQueue)
                .to(exchange)
                .with("delivery.assigned");
    }
}