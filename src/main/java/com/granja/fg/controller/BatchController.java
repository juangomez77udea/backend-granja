package com.granja.fg.controller;

import com.granja.fg.exception.ValueDontFindException;
import com.granja.fg.model.Batch;
import com.granja.fg.service.IBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("fg-app")
@CrossOrigin(value = "http://localhost:5173")
public class BatchController {
    private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

    @Autowired
    private IBatchService batchService;

    @GetMapping("/lotes")
    public List<Batch> getBatches() {
        var batches = batchService.batchesList();
        batches.forEach((batch -> logger.info(batch.toString())));
        return batches;
    }

    @PostMapping("/lotes")
    public Batch addBatch(@RequestBody Batch batch) {
        logger.info("Lote agregado" +batch.toString());
        return batchService.saveBatch(batch);
    }

    @GetMapping("lotes/{idBatch}")
    public ResponseEntity<Batch>
    getBatchById(@PathVariable Integer idBatch) {
        Batch batch = batchService.findBatchById(idBatch);
        if(batch == null) throw new ValueDontFindException("No se encontró el lote"+ idBatch);
        ResponseEntity<Batch> ok = ResponseEntity.ok(batch);
        return ok;
    }

    @PutMapping("/lotes/{idBatch}")
    public ResponseEntity<Batch> updateBatch(@PathVariable Integer idBatch, @RequestBody Batch batchReceived) {
        Batch existingBatch = batchService.findBatchById(idBatch);
        if(existingBatch == null)
            throw new ValueDontFindException("El id recibido no existe: " + idBatch);

        // Manejar actualización de 'amount' y ajustar 'pendingAmount' en consecuencia
        if(batchReceived.getAmount() != null && !batchReceived.getAmount().equals(existingBatch.getAmount())) {
            Integer diferencia = batchReceived.getAmount() - existingBatch.getAmount();
            existingBatch.setAmount(batchReceived.getAmount());

            // Actualizar 'pendingAmount' basado en la diferencia
            existingBatch.setPendingAmount(existingBatch.getPendingAmount() + diferencia);

            // Asegurar que 'pendingAmount' no sea negativa
            if(existingBatch.getPendingAmount() < 0) {
                throw new IllegalArgumentException("La cantidad pendiente no puede ser negativa después de la actualización.");
            }
        }

        // Actualizar otros campos si están presentes
        if(batchReceived.getDateIn() != null){
            existingBatch.setDateIn(batchReceived.getDateIn());
        }
        if(batchReceived.getBatchAge() != null){
            existingBatch.setBatchAge(batchReceived.getBatchAge());
        }

        batchService.saveBatch(existingBatch);

        return ResponseEntity.ok(existingBatch);
    }

    @DeleteMapping("/lotes/{idBatch}")
    public ResponseEntity<Map<String, Boolean>> deleteBatch(@PathVariable Integer idBatch) {
        Batch batch = batchService.findBatchById(idBatch);
        if(batch == null) throw new ValueDontFindException("El id recibido no existe: " +idBatch);
        batchService.deleteBatch(batch);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
