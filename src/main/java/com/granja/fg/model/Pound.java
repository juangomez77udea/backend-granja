package com.granja.fg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPound;

    private Integer idBatch;
    private Date datePound;
    private String poundType;
    private Integer quantityIn;
    private Integer averageWeightUnit;
    private Integer averageWeightBatch;
    private String occupedPound;

}
