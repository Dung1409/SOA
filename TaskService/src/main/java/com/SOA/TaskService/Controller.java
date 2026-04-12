package com.SOA.TaskService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SOA.TaskService.Events.OrderRequest;
import com.SOA.TaskService.OrderSagaTracker.OrderTrackingStatus;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class Controller {

    RabbitTemplate rabbitTemplate;
    OrderSagaTracker orderSagaTracker;

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @PostMapping("/order")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderRequest orderRequest) {

        String requestId = orderRequest.getRequestId();
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
            orderRequest.setRequestId(requestId);
        }

        orderSagaTracker.markSubmitted(requestId);

        rabbitTemplate.convertAndSend("order.exchange", "order.create", orderRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "requestId", requestId,
                "status", "ORDER_SUBMITTED",
                "message", "Order request accepted. Track result by requestId."));
    }

    @GetMapping("/order/status/{requestId}")
    public ResponseEntity<?> getOrderStatus(@PathVariable String requestId) {
        OrderTrackingStatus status = orderSagaTracker.getByRequestId(requestId);
        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "RequestId not found", "requestId", requestId));
        }
        return ResponseEntity.ok(status);
    }

}
