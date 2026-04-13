package com.SOA.PaymentService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.SOA.PaymentService.Events.PaymentFailedEvent;
import com.SOA.PaymentService.Events.PaymentSuccessEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Consumser {

    final RabbitTemplate rabbitTemplate;
    final PaymentRepository paymentRepository;

    @RabbitListener(queues = "payment.request.queue")
    void handlePaymentRequest(PaymentRequest request) {
        System.out.println("Received payment request for order: " + request.orderId());
        // Simulate payment processing
        boolean paymentSuccess = Math.random() > 0.5; // Random success/failure

        Payment payment = new Payment();
        payment.setOrderId(request.orderId());
        payment.setAmount(request.amount());

        if (paymentSuccess) {
            System.out.println("Payment successful for order: " + request.orderId());
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);
            rabbitTemplate.convertAndSend("order.exchange", "payment.success",
                    new PaymentSuccessEvent(request.orderId()));
        } else {
            System.out.println("Payment failed for order: " + request.orderId());
            payment.setStatus("FAILED");
            paymentRepository.save(payment);
            rabbitTemplate.convertAndSend("order.exchange", "payment.failed",
                    new PaymentFailedEvent(request.orderId()));
        }
    }
}
