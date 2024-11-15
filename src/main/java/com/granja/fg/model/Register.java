package com.granja.fg.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegister;

    @ManyToOne
    @JoinColumn(name = "id_pound")
    private Pound pound;

    private Date date;
    private Integer feedingAmount;
    private Integer mortality;
    private String extraEvent;
    private Integer remainingAnimals;
}