package com.granja.fg.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idBatch;
    Integer amount;
    Integer pendingAmount;

    public Integer getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(Integer pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date dateIn;
    Integer batchAge;

    public Integer getIdBatch() {
        return idBatch;
    }

    public void setIdBatch(Integer idBatch) {
        this.idBatch = idBatch;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Integer getBatchAge() {
        return batchAge;
    }

    public void setBatchAge(Integer batchAge) {
        this.batchAge = batchAge;
    }

    public Batch(Integer amount, Integer pendingAmount, Date dateIn) {
        this.amount = amount;
        this.pendingAmount = pendingAmount;
        this.dateIn = dateIn;
    }
}
