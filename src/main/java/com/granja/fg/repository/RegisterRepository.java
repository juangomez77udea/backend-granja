package com.granja.fg.repository;

import com.granja.fg.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RegisterRepository extends JpaRepository<Register, Long> {
    @Query("SELECT r FROM Register r WHERE r.date BETWEEN :startDate AND :endDate ORDER BY r.date")
    List<Register> findRegistersBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}