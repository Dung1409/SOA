package com.SOA.OrderService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SOA.OrderService.Events.OrderStatusResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class Controller {
    private final OrderRepository orderRepository;

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    @GetMapping("/status/{requestId}")
    public ResponseEntity<OrderStatusResponse> getOrderStatus(@PathVariable String requestId) {
        return orderRepository.findByRequestId(requestId)
                .map(order -> {
                    OrderStatusResponse response = new OrderStatusResponse();
                    response.setRequestId(requestId);
                    response.setOrderId(order.getOrderId());
                    response.setStatus(order.getStatus());
                    response.setMessage("Order status: " + order.getStatus().getDisplayName());
                    response.setUpdatedAt(order.getUpdatedAt().toString());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}