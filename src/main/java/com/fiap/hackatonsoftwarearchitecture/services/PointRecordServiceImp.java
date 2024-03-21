package com.fiap.hackatonsoftwarearchitecture.services;

import com.fiap.hackatonsoftwarearchitecture.repositories.PointRecordRepository;
import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointRecordServiceImp implements PointRecordService {

    private PointRecordRepository repository;

    @Override
    public void register(RecordDTO recordDTO) {
        Record recordEntity = Record.buildEntity(recordDTO);
        repository.save(recordEntity);
    }

    @Override
    public List<RecordDTO> getRecordByUserId(Long userId, LocalDateTime start, LocalDateTime end) {
        return null;
    }
}


