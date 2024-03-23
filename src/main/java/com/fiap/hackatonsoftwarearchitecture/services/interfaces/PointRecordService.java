package com.fiap.hackatonsoftwarearchitecture.services.interfaces;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.ReportDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordViewDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PointRecordService {

    void register(RecordDTO recordDTO);
    ReportDTO getReportDailyByEmailAndDate(String email, LocalDate date);
    List<RecordViewDTO> getRecordsByEmailAndDate(String email, LocalDate date);
    ReportDTO getReportCurrentMonthly(String email, int month, int year);
}
