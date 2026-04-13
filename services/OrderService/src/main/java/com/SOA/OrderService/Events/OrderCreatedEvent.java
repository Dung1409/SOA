package com.SOA.OrderService.Events;

public record OrderCreatedEvent(String orderId, Double amount, String requestId) {

}
