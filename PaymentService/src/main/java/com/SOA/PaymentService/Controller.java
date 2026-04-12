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

}