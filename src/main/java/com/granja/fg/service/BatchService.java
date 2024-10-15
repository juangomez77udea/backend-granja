package com.granja.fg.service;

import com.granja.fg.model.Batch;
import com.granja.fg.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService implements IBatchService {
    @Autowired
    private BatchRepository batchRepository;

    @Override
    public List<Batch> batchesList() {
        return batchRepository.findAll();
    }

    @Override
    public Batch findBatchById(Integer idBatch) {
        return batchRepository.findById(idBatch).orElse(null);
    }

    @Override
    public Batch saveBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    @Override
    public void deleteBatch(Batch batch) {
        batchRepository.delete(batch);
    }

}
