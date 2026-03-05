package com.payment.controller;

import com.payment.model.Transaction;
import com.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/send")
    public Transaction sendMoney(@RequestBody Transaction tx) {

        return paymentService.sendMoney(tx);

    }

}
