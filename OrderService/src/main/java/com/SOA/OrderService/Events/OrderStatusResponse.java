package com.SOA.OrderService.Events;

import com.SOA.OrderService.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusResponse {
    private String requestId;
    private String orderId;
    private OrderStatus status;
    private String message;
    private String updatedAt;
}
