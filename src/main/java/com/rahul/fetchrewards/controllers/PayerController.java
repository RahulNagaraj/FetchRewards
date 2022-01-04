package com.rahul.fetchrewards.controllers;

import com.rahul.fetchrewards.models.Payer;
import com.rahul.fetchrewards.models.Transaction;
import com.rahul.fetchrewards.services.ConsumerServiceImpl;
import com.rahul.fetchrewards.services.PayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payer")
public class PayerController {
    private final PayerServiceImpl payerService;

    @Autowired
    public PayerController(PayerServiceImpl payerService) {
        this.payerService = payerService;
    }

    @PostMapping(path = "/transactions", consumes = "application/json")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {

        try {
            Transaction t = payerService.addTransaction(transaction);
            return new ResponseEntity<>(t, HttpStatus.OK);
        } catch (IllegalStateException exception) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/balances", produces = "application/json")
    Map<String, Integer> getAllPayerBalances() {
        return payerService.getAllPayerBalances();
    }
}
