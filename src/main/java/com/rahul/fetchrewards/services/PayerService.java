package com.rahul.fetchrewards.services;

import com.rahul.fetchrewards.models.Payer;
import com.rahul.fetchrewards.models.Transaction;

import java.util.List;
import java.util.Map;

public interface PayerService {
    Transaction addTransaction(Transaction transaction);
    Map<String, Integer> getAllPayerBalances();
}
