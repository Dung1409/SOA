package com.SOA.TaskService;

import org.springframework.amqp.core.*;
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

    // ===== QUEUES =====
    /*
     * order create queue
     */
    @Bean
    public Queue orderCreateQueue() {
        return new Queue("order.create.queue", true);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue("order.created.queue", true);
    }

    @Bean

    public Queue orderCancelQueue() {
        return new Queue("order.cancel.queue", true);
    }

    /*
     * payment request queue
     * result of payment: success -> payment.success.queue
     * failed -> payment.failed.queue
     */

    @Bean
    public Queue paymentRequestQueue() {
        return new Queue("payment.request.queue", true);
    }

    @Bean
    public Queue paymentSuccessQueue() {
        return new Queue("payment.success.queue", true);
    }

    @Bean
    public Queue paymentFailedQueue() {
        return new Queue("payment.failed.queue", true);
    }

    /*
     * delivery request queue
     * result of delivery: assigned -> delivery.assigned.queue
     */

    @Bean
    public Queue deliveryRequestQueue() {
        return new Queue("delivery.request.queue", true);
    }

    @Bean
    public Queue deliveryAssignedQueue() {
        return new Queue("delivery.assigned.queue", true);
    }

    // ===== BINDINGS =====

    @Bean
    public Binding orderCreateBinding(Queue orderCreateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderCreateQueue)
                .to(exchange)
                .with("order.create");
    }

    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderCreatedQueue)
                .to(exchange)
                .with("order.created");
    }

    @Bean
    public Binding orderCancelBinding(Queue orderCancelQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderCancelQueue)
                .to(exchange)
                .with("order.cancel");
    }

    @Bean
    public Binding paymentRequestBinding(Queue paymentRequestQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentRequestQueue)
                .to(exchange)
                .with("payment.request");
    }

    @Bean
    public Binding paymentSuccessBinding(Queue paymentSuccessQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentSuccessQueue)
                .to(exchange)
                .with("payment.success");
    }

    @Bean
    public Binding paymentFailedBinding(Queue paymentFailedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentFailedQueue)
                .to(exchange)
                .with("payment.failed");
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