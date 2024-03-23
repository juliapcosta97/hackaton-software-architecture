package com.fiap.hackatonsoftwarearchitecture.services;

import com.fiap.hackatonsoftwarearchitecture.repositories.PointRecordRepository;
import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.DailyReportDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordViewDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public DailyReportDTO getReportDailyByEmailAndDate(String email, LocalDate date) {
        List<Record> records = getRecordsAndSort(email, date);

        DailyReportDTO dailyReport = new DailyReportDTO();
        dailyReport.setEntryHour(records.get(0).getDateCreated());
        dailyReport.setExitHour(records.get(records.size() - 1).getDateCreated());

        calculateBreaks(records, dailyReport);
        calculateTotalHoursWorked(dailyReport);

        return dailyReport;
    }

    @Override
    public List<RecordViewDTO> getRecordsByEmailAndDate(String email, LocalDate date) {
        List<Record> records = getRecordsAndSort(email, date);
        List<RecordViewDTO> recordDetaiList = new ArrayList<>();

        records.stream().forEach(record -> {
            RecordViewDTO recordDetailDTO = new RecordViewDTO(record.getComments(), record.getDateCreated());
            recordDetaiList.add(recordDetailDTO);
        });

        return recordDetaiList;
    }

    private List<Record> getRecordsAndSort(String email, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Record> records = repository.findByEmailAndDateCreatedBetween(email, startOfDay, endOfDay);
        records.sort(Comparator.comparing(Record::getDateCreated));

        return records;
    }

    private void calculateBreaks(List<Record> records, DailyReportDTO dailyReport) {
        LocalDateTime startBreak = null;
        List<DailyReportDTO.Break> breaks = new ArrayList<>();

        for (Record record : records) {
            LocalDateTime dateTime = record.getDateCreated();
            if (startBreak == null) {
                startBreak = dateTime;
            } else {
                breaks.add(new DailyReportDTO.Break(startBreak, dateTime));
                startBreak = null;
            }
        }
        dailyReport.setBreaks(breaks);
    }

    private void calculateTotalHoursWorked(DailyReportDTO dailyReport) {
        Duration totalHours = Duration.ZERO;
        for (DailyReportDTO.Break aBreak : dailyReport.getBreaks()) {
            totalHours = totalHours.plus(Duration.between(aBreak.getStart(), aBreak.getEnd()));
        }
        dailyReport.setTotalHoursWorked(totalHours);
    }
}


