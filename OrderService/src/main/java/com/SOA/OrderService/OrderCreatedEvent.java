package com.SOA.OrderService;

public record OrderCreatedEvent(String orderId, Double amount, String requestId) {

}
