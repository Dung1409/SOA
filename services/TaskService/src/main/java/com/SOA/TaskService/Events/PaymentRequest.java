package com.SOA.TaskService.Events;

public record PaymentRequest(String orderId, Double amount) {

}
