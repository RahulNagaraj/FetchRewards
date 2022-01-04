package com.rahul.fetchrewards.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"transactionId"})
public class Transaction implements Comparable<Transaction> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    private String payer;
    private Integer points;
    private Date timestamp;
    @JsonIgnore
    private boolean isVisited = false;

    @Override
    public int compareTo(Transaction transaction) {
        return this.timestamp.compareTo(transaction.getTimestamp());
    }
}
