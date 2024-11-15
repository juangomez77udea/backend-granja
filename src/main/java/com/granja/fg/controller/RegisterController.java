package com.granja.fg.controller;

import com.granja.fg.model.Register;
import com.granja.fg.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fg-app/registros")
@CrossOrigin(value = "http://localhost:5173")
public class RegisterController {

    @Autowired
    private IRegisterService registerService;

    @GetMapping
    public List<Register> getAllRegisters() {
        return registerService.getAllRegisters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Register> getRegisterById(@PathVariable Long id) {
        Register register = registerService.getRegisterById(id);
        return register != null ? ResponseEntity.ok(register) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createRegister(@RequestBody Register register) {
        try {
            Register savedRegister = registerService.saveRegister(register);
            return ResponseEntity.ok(savedRegister);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al guardar el registro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegister(@PathVariable Long id) {
        registerService.deleteRegister(id);
        return ResponseEntity.noContent().build();
    }
}