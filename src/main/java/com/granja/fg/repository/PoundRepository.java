package com.granja.fg.repository;

import com.granja.fg.model.Pound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoundRepository extends JpaRepository <Pound, Integer> {
    List<Pound> findByOccupedPound(String occupedPound);
}
