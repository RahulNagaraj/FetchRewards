package com.rahul.fetchrewards.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payer implements Serializable {
    @Id
    private String payer;
    private Integer points;
}
