package com.fiap.hackatonsoftwarearchitecture.services.interfaces;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.ReportDailyDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordViewDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.ReportMonthlyDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PointRecordService {

    void register(RecordDTO recordDTO);
    ReportDailyDTO getReportDailyByEmailAndDate(String email, LocalDate date);
    List<RecordViewDTO> getRecordsByEmailAndDate(String email, LocalDate date);
    ReportMonthlyDTO getReportCurrentMonthly(String email, int month, int year);
}
