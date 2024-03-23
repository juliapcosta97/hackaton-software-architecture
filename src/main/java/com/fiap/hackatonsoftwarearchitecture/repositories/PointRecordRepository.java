package com.fiap.hackatonsoftwarearchitecture.repositories;

import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointRecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByEmailAndDateCreatedBetween(String email, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
