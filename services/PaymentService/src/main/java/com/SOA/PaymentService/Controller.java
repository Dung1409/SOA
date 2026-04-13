package com.SOA.PaymentService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/payment")
public class Controller {
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/pay/{amount}")
    public String processPayment(@PathVariable double amount) {
        // Implementation for processing payment
        return "Payment processed for amount: " + amount;
    }
}