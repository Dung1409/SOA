package com.SOA.PaymentService;

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
    public Queue paymentRequestQueue() {
        return new Queue("payment.request.queue", true);
    }

    @Bean
    public Binding paymentRequestBinding(Queue paymentRequestQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentRequestQueue)
                .to(exchange)
                .with("payment.request");
    }

}