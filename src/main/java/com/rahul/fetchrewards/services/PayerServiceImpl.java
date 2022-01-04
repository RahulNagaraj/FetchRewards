package com.rahul.fetchrewards.services;

import com.rahul.fetchrewards.models.Payer;
import com.rahul.fetchrewards.models.Transaction;
import com.rahul.fetchrewards.repository.PayerRepository;
import com.rahul.fetchrewards.repository.TransactionRepository;
import com.rahul.fetchrewards.utility.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayerServiceImpl implements PayerService {
    Logger logger = LoggerFactory.getLogger(PayerServiceImpl.class);
    @Autowired
    private final PayerRepository payerRepository;
    @Autowired
    private final TransactionRepository transactionRepository;

    public PayerServiceImpl(PayerRepository payerRepository,
                            TransactionRepository transactionRepository) {
        this.payerRepository = payerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        String payerName = transaction.getPayer();
        Integer points = transaction.getPoints();

        Payer payer = payerRepository.findByPayer(payerName);
        if (payer == null) {
            payer = new Payer(payerName, 0);
        }
        int updatedPoints = points + payer.getPoints();
        if (updatedPoints < 0) {
            throw new IllegalStateException("Unable to make the transaction");
        }
        payer.setPoints(updatedPoints);
        payerRepository.save(payer);
        return transactionRepository.save(transaction);
    }

    @Override
    public Map<String, Integer> getAllPayerBalances() {
        List<Payer> payerList = payerRepository.findAll();

        return Util.getPayerPoints(payerList);
    }
}
