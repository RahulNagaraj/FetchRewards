package com.rahul.fetchrewards.services;

import com.rahul.fetchrewards.models.Payer;
import com.rahul.fetchrewards.models.Points;
import com.rahul.fetchrewards.models.Transaction;
import com.rahul.fetchrewards.repository.PayerRepository;
import com.rahul.fetchrewards.repository.TransactionRepository;
import com.rahul.fetchrewards.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final PayerRepository payerRepository;

    public ConsumerServiceImpl(TransactionRepository transactionRepository,
                               PayerRepository payerRepository) {
        this.transactionRepository = transactionRepository;
        this.payerRepository = payerRepository;
    }

    @Override
    @Transactional
    @Modifying
    public List<Payer> spendPoints(Points points) {
        if(!Util.validatePoints(points.getPoints(), payerRepository))
            throw new IllegalStateException("Insufficient points");

        int pointsToSubract = points.getPoints();

        List<Transaction> allTransaction = transactionRepository.findAll();
        List<Payer> payerList = payerRepository.findAll();
        List<Payer> resultPayerList = new ArrayList<>();

        HashMap<String,Payer> payerPointsMap = getPayerPointsMap(payerList);
        HashMap<String, Integer> resultMap = new HashMap<>();

        Collections.sort(allTransaction);

        for(Transaction transaction : allTransaction ) {

            if(transaction.isVisited()) continue;

            //Balance payer = payerBalanceMap.get(transaction.getPayer());
            if(!resultMap.containsKey(transaction.getPayer())) {
                resultMap.put(transaction.getPayer(), 0);
            }
            // If transaction is negative we need to add that point in the spend points because those transaction was already added
            if(transaction.getPoints() <= 0) {
                pointsToSubract += transaction.getPoints()*-1;
                resultMap.put(transaction.getPayer(), resultMap.get(transaction.getPayer())+(transaction.getPoints()*-1));
                transaction.setVisited(true);
            }
            else if(pointsToSubract >= transaction.getPoints()) {
                resultMap.put(transaction.getPayer(), resultMap.get(transaction.getPayer())-transaction.getPoints());
                pointsToSubract -= transaction.getPoints();
                transaction.setVisited(true);
            }
            else if(pointsToSubract < transaction.getPoints()) {
                int diff = transaction.getPoints()- pointsToSubract;
                resultMap.put(transaction.getPayer(), resultMap.get(transaction.getPayer()) - pointsToSubract);
                pointsToSubract = 0;
                transaction.setPoints(diff);
            }
            transactionRepository.save(transaction);
            if(pointsToSubract == 0) break;
        }
        for(String payer: resultMap.keySet()) {
            Payer payer1 = payerPointsMap.get(payer);
            payer1.setPoints(payer1.getPoints() + resultMap.get(payer));
            resultPayerList.add(new Payer(payer, resultMap.get(payer)));
            payerRepository.save(payer1);
        }
        return resultPayerList;
    }

    private HashMap<String, Payer> getPayerPointsMap(List<Payer> payerList){
        HashMap<String, Payer> payerPointsMap = new HashMap<>();
        for(Payer payer : payerList) {
            payerPointsMap.put(payer.getPayer(), payer);
        }
        return payerPointsMap;
    }

}
