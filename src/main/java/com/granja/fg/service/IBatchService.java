package com.granja.fg.service;

import com.granja.fg.model.Batch;

import java.util.List;

public interface IBatchService {
    List<Batch> batchesList();
    Batch findBatchById(Integer idBatch);
    Batch saveBatch(Batch batch);
    void deleteBatch(Batch batch);
    Batch updateBatchQuantity(Integer idBatch, int cantidadIngresada);
}
