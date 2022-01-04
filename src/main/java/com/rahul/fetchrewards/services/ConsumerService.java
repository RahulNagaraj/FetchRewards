package com.rahul.fetchrewards.services;

import com.rahul.fetchrewards.models.Payer;
import com.rahul.fetchrewards.models.Points;

import java.util.List;

public interface ConsumerService {
    List<Payer> spendPoints(Points points);
}
