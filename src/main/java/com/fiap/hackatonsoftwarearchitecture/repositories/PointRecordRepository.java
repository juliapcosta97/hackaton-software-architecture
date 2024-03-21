package com.fiap.hackatonsoftwarearchitecture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointRecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUserIdAndStartBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
