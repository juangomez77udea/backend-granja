package com.granja.fg.service;

import com.granja.fg.model.Register;
import com.granja.fg.model.Pound;
import com.granja.fg.repository.RegisterRepository;
import com.granja.fg.repository.PoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RegisterService implements IRegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private PoundRepository poundRepository;

    @Override
    public List<Register> getAllRegisters() {
        return registerRepository.findAll();
    }

    @Override
    public Register getRegisterById(Long id) {
        return registerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Register saveRegister(Register register) {
        if (register.getPound() == null || register.getPound().getIdPound() == null) {
            throw new IllegalArgumentException("El registro debe estar asociado a un estanque válido");
        }

        Optional<Pound> poundOptional = poundRepository.findById(Math.toIntExact(register.getPound().getIdPound()));
        if (poundOptional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el estanque con ID: " + register.getPound().getIdPound());
        }

        Pound pound = poundOptional.get();
        pound.setQuantityIn(register.getRemainingAnimals());
        poundRepository.save(pound);

        register.setPound(pound);
        return registerRepository.save(register);
    }

    @Override
    public void deleteRegister(Long id) {
        registerRepository.deleteById(id);
    }
}