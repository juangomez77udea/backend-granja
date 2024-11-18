package com.granja.fg.service;

import com.granja.fg.model.Register;
import com.granja.fg.model.Pound;
import com.granja.fg.repository.RegisterRepository;
import com.granja.fg.repository.PoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

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

    @Override
    public Map<String, List<Object>> getStatistics(String dataType, String timeFrame) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;

        if ("week".equals(timeFrame)) {
            startDate = endDate.minusDays(6);
        } else if ("month".equals(timeFrame)) {
            startDate = endDate.withDayOfMonth(1);
        } else {
            throw new IllegalArgumentException("Timeframe no válido");
        }

        List<Register> registers = registerRepository.findRegistersBetweenDates(
                Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        Map<String, List<Object>> result = new LinkedHashMap<>();
        List<String> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        if ("week".equals(timeFrame)) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                String label = date.format(formatter);
                labels.add(label);
                LocalDate finalDate = date;
                int value = registers.stream()
                        .filter(r -> r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(finalDate))
                        .mapToInt(r -> "mortality".equals(dataType) ? r.getMortality() : r.getFeedingAmount())
                        .sum();
                values.add(value);
            }
        } else {
            Map<Integer, Integer> weeklyData = registers.stream()
                    .collect(Collectors.groupingBy(
                            r -> r.getDate().toInstant().atZone(ZoneId.systemDefault()).get(WeekFields.ISO.weekOfMonth()),
                            Collectors.summingInt(r -> "mortality".equals(dataType) ? r.getMortality() : r.getFeedingAmount())
                    ));

            for (int i = 1; i <= 5; i++) {
                labels.add("Semana " + i);
                values.add(weeklyData.getOrDefault(i, 0));
            }
        }

        result.put("labels", new ArrayList<>(labels));
        result.put("values", new ArrayList<>(values));

        return result;
    }

}