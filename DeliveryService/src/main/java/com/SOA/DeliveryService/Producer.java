package com.SOA.DeliveryService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final DeliveryRepository deliveryRepository;

    @RabbitListener(queues = "delivery.request.queue")
    void handleDeliveryRequest(DeliveryRequest request) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(request.orderId());
        delivery.setStatus("ASSIGNED");
        deliveryRepository.save(delivery);

        System.out.println("Delivery assigned for order: " + request.orderId());
        rabbitTemplate.convertAndSend("order.exchange", "delivery.assigned",
                new DeliveryAssignedEvent(request.orderId()));
    }
}
