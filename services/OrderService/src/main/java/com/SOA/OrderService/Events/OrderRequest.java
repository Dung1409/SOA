package com.SOA.OrderService.Events;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
    private String requestId;

    @NotNull
    private Double amount;
    @NotBlank
    private String phone;

    private String description;

    @NotBlank
    private String address;

    private List<String> menuItemIds;

}
