package com.granja.fg.service;

import com.granja.fg.model.Register;
import java.util.List;

public interface IRegisterService {
    List<Register> getAllRegisters();
    Register getRegisterById(Long id);
    Register saveRegister(Register register);
    void deleteRegister(Long id);
}