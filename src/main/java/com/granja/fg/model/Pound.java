package com.granja.fg.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Integer idPound;

    private Integer idBatch;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date datePound;

    private String poundType;

    private Integer quantityIn;

    private Integer quantityOut;

    private Integer averageWeightUnit;

    private Integer averageWeightBatch;

    @Override
    public String toString() {
        return "Pound{" +
                "idPound=" + idPound +
                ", idBatch=" + idBatch +
                ", datePound=" + datePound +
                ", poundType='" + poundType + '\'' +
                ", quantityIn=" + quantityIn +
                ", quantityOut=" + quantityOut +
                ", averageWeightUnit=" + averageWeightUnit +
                ", averageWeightBatch=" + averageWeightBatch +
                '}';
    }
}
