package com.fiap.hackatonsoftwarearchitecture.repositories;

import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRecordRepository extends JpaRepository<Record, Long> {

}
