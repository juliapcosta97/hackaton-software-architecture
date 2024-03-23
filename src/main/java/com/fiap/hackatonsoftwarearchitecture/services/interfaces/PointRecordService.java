package com.fiap.hackatonsoftwarearchitecture.services.interfaces;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDetailDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PointRecordService {

    void register(RecordDTO recordDTO);
    List<RecordDetailDTO> getRecordsByEmailAndDate(String email, LocalDate date);
}
