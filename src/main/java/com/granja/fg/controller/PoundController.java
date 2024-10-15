package com.granja.fg.controller;

import com.granja.fg.exception.ValueDontFindException;
import com.granja.fg.model.Pound;
import com.granja.fg.service.IPoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fg-app")
@CrossOrigin(value = "http://localhost:5173")
public class PoundController {
    private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

    @Autowired
    private IPoundService poundService;

    @GetMapping("/estanques")
    public List<Pound> getPounds() {
        var pounds = poundService.poundList();
        pounds.forEach(pound -> logger.info(pound.toString()));
        return pounds;
    }

    @PostMapping("/estanques")
    public ResponseEntity<Pound> addPound(@RequestBody Pound pound) {
        try {
            logger.info("Estanque agregado: " + pound.toString());
            Pound savedPound = poundService.savePound(pound);
            logger.info("Estnque guardado con ID:" + savedPound.getIdPound());
            return ResponseEntity.ok(savedPound);
        } catch (Exception e) {
            logger.error("Error al guardar el estanque", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/estanques/{idPound}")
    public ResponseEntity<Pound> updatePound(@PathVariable Integer idPound, @RequestBody Pound poundReceived) {
        Pound pound = poundService.findPoundById(idPound);
        if (pound == null)
            throw new ValueDontFindException("El id recibido no existe: " +idPound);
        pound.setAverageWeightUnit(poundReceived.getAverageWeightUnit());
        pound.setPoundType(poundReceived.getPoundType());
        pound.setIdPound(idPound);
        pound.setQuantityIn(poundReceived.getQuantityIn());
        pound.setQuantityOut(poundReceived.getQuantityOut());
        pound.setAverageWeightBatch(poundReceived.getAverageWeightBatch());

        poundService.savePound(pound);

        return ResponseEntity.ok(pound);
    }

    @DeleteMapping("/estanques/{idPound}")
    public ResponseEntity<Void> deletePound(@PathVariable Integer idPound) {
        Pound pound = poundService.findPoundById(idPound);
        if (pound == null)
            throw new ValueDontFindException("No se encontró el estanque con id " + idPound);

        poundService.deletePound(idPound);
        return ResponseEntity.noContent().build();
    }


}
