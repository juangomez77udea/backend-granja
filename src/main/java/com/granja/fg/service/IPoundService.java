package com.granja.fg.service;

import com.granja.fg.model.Pound;

import java.util.List;

public interface IPoundService {
    List<Pound> poundList();
    Pound findPoundById(Integer idPound);
    Pound savePound(Pound pound);
    void deletePound(Integer idPound);
}
