package com.granja.fg.repository;

import com.granja.fg.model.Pound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoundRepository extends JpaRepository <Pound, Integer> {
}
