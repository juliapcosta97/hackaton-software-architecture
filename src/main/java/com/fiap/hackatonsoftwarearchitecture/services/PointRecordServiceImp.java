package com.fiap.hackatonsoftwarearchitecture.services;

import com.fiap.hackatonsoftwarearchitecture.repositories.PointRecordRepository;
import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordViewDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.ReportDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
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
    public ReportDTO getReportDailyByEmailAndDate(String email, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return generateReport(email, startOfDay, endOfDay);
    }

    @Override
    public ReportDTO getReportCurrentMonthly(String email, int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endDate = yearMonth.atEndOfMonth();

        LocalDateTime startOfMonth = startDate.atStartOfDay();
        LocalDateTime endOfMonth = endDate.atTime(23, 59, 59);

        return generateReport(email,startOfMonth, endOfMonth);
    }

    @Override
    public List<RecordViewDTO> getRecordsByEmailAndDate(String email, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Record> records = getRecordsAndSort(email, startOfDay, endOfDay);
        List<RecordViewDTO> recordDetaiList = new ArrayList<>();

        records.stream().forEach(record -> {
            RecordViewDTO recordDetailDTO = new RecordViewDTO(record.getComments(), record.getDateCreated());
            recordDetaiList.add(recordDetailDTO);
        });

        return recordDetaiList;
    }

    private ReportDTO generateReport(String email, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<Record> records = getRecordsAndSort(email, startOfDay, endOfDay);

        if(records.isEmpty()) {
            return null;
        }

        ReportDTO dailyReport = new ReportDTO();
        dailyReport.setEntryHour(records.get(0).getDateCreated());
        dailyReport.setExitHour(records.get(records.size() - 1).getDateCreated());

        calculateBreaks(records, dailyReport);
        calculateTotalHoursWorked(dailyReport);

        return dailyReport;
    }

    private List<Record> getRecordsAndSort(String email, LocalDateTime start, LocalDateTime end) {
        List<Record> records = repository.findByEmailAndDateCreatedBetween(email, start, end);

        if(!records.isEmpty()) {
            records.sort(Comparator.comparing(Record::getDateCreated));
        }

        return records;
    }

    private void calculateBreaks(List<Record> records, ReportDTO dailyReport) {
        LocalDateTime startBreak = null;
        List<ReportDTO.Break> breaks = new ArrayList<>();

        for (Record record : records) {
            LocalDateTime dateTime = record.getDateCreated();
            if (startBreak == null) {
                startBreak = dateTime;
            } else {
                breaks.add(new ReportDTO.Break(startBreak, dateTime));
                startBreak = null;
            }
        }
        dailyReport.setBreaks(breaks);
    }

    private void calculateTotalHoursWorked(ReportDTO dailyReport) {
        Duration totalHours = Duration.ZERO;
        for (ReportDTO.Break aBreak : dailyReport.getBreaks()) {
            totalHours = totalHours.plus(Duration.between(aBreak.getStart(), aBreak.getEnd()));
        }
        dailyReport.setTotalHoursWorked(totalHours);
    }
}


