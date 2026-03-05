package com.payment.service;

import com.payment.model.Transaction;
import com.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final TransactionRepository repo;

    public PaymentService(TransactionRepository repo) {
        this.repo = repo;
    }

    public Transaction sendMoney(Transaction tx) {

        return repo.save(tx);

    }

}
