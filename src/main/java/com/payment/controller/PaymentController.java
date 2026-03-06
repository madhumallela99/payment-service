package com.payment.controller;

import com.payment.model.Transaction;
import com.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/payment")

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Health check endpoint
    @GetMapping("/test")
    public String test() {
        return "Payment Service Running";
    }

    // Send money
    @PostMapping("/send")
    public Transaction sendMoney(@RequestBody Transaction tx) {
        return paymentService.sendMoney(tx);
    }

    // Get all transactions
    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return paymentService.getAllTransactions();
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable Long id) {
        return paymentService.getTransactionById(id);
    }

    // Delete transaction
    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        paymentService.deleteTransaction(id);
        return "Transaction deleted successfully";
    }

}
