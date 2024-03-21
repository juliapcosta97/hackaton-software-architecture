package com.fiap.hackatonsoftwarearchitecture.services.interfaces;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface PointRecordService {

    void register(RecordDTO recordDTO);
    List<RecordDTO> getRecordByUserId(Long userId, LocalDateTime start, LocalDateTime end);
}
