package com.SOA.TaskService.Events;

public record OrderCreatedEvent(String orderId, Double amount, String requestId) {

}
