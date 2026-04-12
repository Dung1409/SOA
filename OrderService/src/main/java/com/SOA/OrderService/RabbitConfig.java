package com.SOA.OrderService;

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
    public Queue orderCreateQueue() {
        return new Queue("order.create.queue", true);
    }

    @Bean
    public Queue orderCancelQueue() {
        return new Queue("order.cancel.queue", true);
    }

    @Bean
    public Queue orderPaymentSuccessQueue() {
        return new Queue("order.payment.success.queue", true);
    }

    @Bean
    public Queue orderDeliveryAssignedQueue() {
        return new Queue("order.delivery.assigned.queue", true);
    }

    @Bean
    public Binding orderCreateBinding(Queue orderCreateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderCreateQueue)
                .to(exchange)
                .with("order.create");
    }

    @Bean
    public Binding orderCancelBinding(Queue orderCancelQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderCancelQueue)
                .to(exchange)
                .with("order.cancel");
    }

    @Bean
    public Binding orderPaymentSuccessBinding(Queue orderPaymentSuccessQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderPaymentSuccessQueue)
                .to(exchange)
                .with("payment.success");
    }

    @Bean
    public Binding orderDeliveryAssignedBinding(Queue orderDeliveryAssignedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderDeliveryAssignedQueue)
                .to(exchange)
                .with("delivery.assigned");
    }

}
