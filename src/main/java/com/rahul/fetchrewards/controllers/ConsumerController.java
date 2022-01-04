package com.rahul.fetchrewards.controllers;

import com.rahul.fetchrewards.models.Payer;
import com.rahul.fetchrewards.models.Points;
import com.rahul.fetchrewards.services.ConsumerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consumer")
public class ConsumerController {
    private final ConsumerServiceImpl consumerService;

    @Autowired
    public ConsumerController(ConsumerServiceImpl consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping(path = "/spend", consumes = "application/json", produces = "application/json")
    ResponseEntity<List<Payer>> spendPoints(@RequestBody Points points) {
        try {
            List<Payer> response = consumerService.spendPoints(points);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException exception) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
