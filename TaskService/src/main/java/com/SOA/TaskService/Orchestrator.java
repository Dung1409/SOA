package com.SOA.TaskService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.SOA.TaskService.Events.DeliveryAssignedEvent;
import com.SOA.TaskService.Events.DeliveryEvent;
import com.SOA.TaskService.Events.OrderCreatedEvent;
import com.SOA.TaskService.Events.PaymentFailedEvent;
import com.SOA.TaskService.Events.PaymentRequest;
import com.SOA.TaskService.Events.PaymentSuccessEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Orchestrator {

    private final RabbitTemplate rabbitTemplate;
    private final OrderSagaTracker orderSagaTracker;

    @RabbitListener(queues = "order.created.queue")
    void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Received order created event: " + event.orderId());
        String requestId = event.requestId();
        if (requestId == null || requestId.isBlank()) {
            requestId = event.orderId();
        }
        orderSagaTracker.markOrderCreated(requestId, event.orderId());
        rabbitTemplate.convertAndSend("order.exchange", "payment.request",
                new PaymentRequest(event.orderId(), event.amount()));
    }

    @RabbitListener(queues = "payment.success.queue")
    void handlePaymentSuccess(PaymentSuccessEvent event) {
        System.out.println("Received payment success event for order: " + event.orderId());
        orderSagaTracker.markPaymentSuccess(event.orderId());
        rabbitTemplate.convertAndSend("order.exchange", "delivery.request", new DeliveryEvent(event.orderId()));
    }

    @RabbitListener(queues = "payment.failed.queue")
    void handlePaymentFailed(PaymentFailedEvent event) {
        System.out.println("Received payment failed event for order: " + event.orderId());
        orderSagaTracker.markPaymentFailed(event.orderId());
        rabbitTemplate.convertAndSend("order.exchange", "order.cancel", event);
    }

    @RabbitListener(queues = "delivery.assigned.queue")
    void handleDeliveryAssigned(DeliveryAssignedEvent event) {
        System.out.println("Order completed: " + event.orderId());
        orderSagaTracker.markDeliveryAssigned(event.orderId());
    }
}
