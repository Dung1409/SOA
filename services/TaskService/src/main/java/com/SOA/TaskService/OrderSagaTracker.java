package com.SOA.TaskService;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

@Component
public class OrderSagaTracker {

    private final ConcurrentMap<String, OrderTrackingStatus> requestStatuses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, String> requestIdByOrderId = new ConcurrentHashMap<>();

    public OrderTrackingStatus markSubmitted(String requestId) {
        return upsert(requestId, null, "ORDER_SUBMITTED", "Order request accepted and queued.");
    }

    public OrderTrackingStatus markOrderCreated(String requestId, String orderId) {
        requestIdByOrderId.put(orderId, requestId);
        return upsert(requestId, orderId, "ORDER_CREATED", "Order was created, waiting for payment.");
    }

    public OrderTrackingStatus markPaymentSuccess(String orderId) {
        String requestId = requestIdByOrderId.get(orderId);
        if (requestId == null) {
            return null;
        }
        return upsert(requestId, orderId, "PAYMENT_SUCCESS", "Payment success, assigning delivery.");
    }

    public OrderTrackingStatus markPaymentFailed(String orderId) {
        String requestId = requestIdByOrderId.get(orderId);
        if (requestId == null) {
            return null;
        }
        return upsert(requestId, orderId, "FAILED", "Payment failed, order cancelled.");
    }

    public OrderTrackingStatus markDeliveryAssigned(String orderId) {
        String requestId = requestIdByOrderId.get(orderId);
        if (requestId == null) {
            return null;
        }
        return upsert(requestId, orderId, "SUCCESS", "Delivery assigned, order completed.");
    }

    public OrderTrackingStatus getByRequestId(String requestId) {
        return requestStatuses.get(requestId);
    }

    private OrderTrackingStatus upsert(String requestId, String orderId, String status, String message) {
        OrderTrackingStatus previous = requestStatuses.get(requestId);
        String resolvedOrderId = orderId != null ? orderId : (previous != null ? previous.orderId() : null);
        OrderTrackingStatus current = new OrderTrackingStatus(
                requestId,
                resolvedOrderId,
                status,
                message,
                Instant.now().toString());
        requestStatuses.put(requestId, current);
        return current;
    }

    public record OrderTrackingStatus(String requestId, String orderId, String status, String message,
            String updatedAt) {
    }
}
