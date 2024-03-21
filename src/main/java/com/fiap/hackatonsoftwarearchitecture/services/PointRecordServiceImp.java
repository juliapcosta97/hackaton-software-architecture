package com.fiap.hackatonsoftwarearchitecture.services;

import com.fiap.hackatonsoftwarearchitecture.repositories.PointRecordRepository;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PointRecordServiceImp implements PointRecordService {

    @Autowired
    private PointRecordRepository repository;

    @Override
    public void register(RecordDTO recordDTO) {
        repository.save(recordDTO);
    }

    @Override
    public List<RecordDTO> getRecordByUserId(Long userId, LocalDateTime start, LocalDateTime end) {
        return null;
    }
}


