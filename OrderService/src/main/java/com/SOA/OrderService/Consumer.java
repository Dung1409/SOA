package com.SOA.OrderService;

import com.SOA.OrderService.Events.DeliveryAssignedEvent;
import com.SOA.OrderService.Events.OrderCancelEvent;
import com.SOA.OrderService.Events.OrderCreatedEvent;
import com.SOA.OrderService.Events.OrderRequest;
import com.SOA.OrderService.Events.PaymentSuccessEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class Consumer {

    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "order.create.queue")
    public void consumeOrderCreate(OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();
        System.out.println("Order created: " + orderId);
        System.out.println("Menu item ids: " + orderRequest.getMenuItemIds());

        Order order = new Order();
        order.setOrderId(orderId);
        order.setAmount(orderRequest.getAmount());
        order.setPhone(orderRequest.getPhone());
        order.setAddress(orderRequest.getAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setRequestId(orderRequest.getRequestId());
        order.setMenuItemIds(toJson(orderRequest));
        orderRepository.save(order);

        rabbitTemplate.convertAndSend("order.exchange", "order.created",
                new OrderCreatedEvent(orderId, orderRequest.getAmount(), orderRequest.getRequestId()));

    }

    @RabbitListener(queues = "order.cancel.queue")
    public void consumeOrderCancel(OrderCancelEvent event) {
        System.out.println("Order cancelled: " + event.orderId());
        orderRepository.findByOrderId(event.orderId()).ifPresent(order -> {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        });
    }

    @RabbitListener(queues = "order.payment.success.queue")
    public void consumePaymentSuccess(PaymentSuccessEvent event) {
        System.out.println("Payment confirmed for order: " + event.orderId());
        orderRepository.findByOrderId(event.orderId()).ifPresent(order -> {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
        });
    }

    @RabbitListener(queues = "order.delivery.assigned.queue")
    public void consumeDeliveryAssigned(DeliveryAssignedEvent event) {
        System.out.println("Order completed: " + event.orderId());
        orderRepository.findByOrderId(event.orderId()).ifPresent(order -> {
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        });
    }

    private String toJson(OrderRequest orderRequest) {
        try {
            return objectMapper.writeValueAsString(orderRequest.getMenuItemIds());
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize menuItemIds", e);
        }
    }
}
