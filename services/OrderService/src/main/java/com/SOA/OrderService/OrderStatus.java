package com.SOA.OrderService;

public enum OrderStatus {
    PENDING("Pending"),
    PAID("Paid"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
