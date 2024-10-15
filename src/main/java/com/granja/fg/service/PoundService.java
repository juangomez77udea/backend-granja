package com.granja.fg.service;

import com.granja.fg.model.Pound;
import com.granja.fg.repository.PoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoundService implements IPoundService {
    @Autowired
    private PoundRepository poundRepository;

    @Override
    public List<Pound> poundList() {
        return poundRepository.findAll();
    }

    @Override
    public Pound findPoundById(Integer idPound) {
        return poundRepository.findById(idPound).orElse(null);
    }

    @Override
    public Pound savePound(Pound pound) {
        return poundRepository.save(pound);
    }

    @Override
    public void deletePound(Integer idPound) {
        poundRepository.deleteById(idPound);
    }
}

