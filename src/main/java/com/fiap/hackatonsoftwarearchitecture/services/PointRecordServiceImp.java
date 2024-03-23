package com.fiap.hackatonsoftwarearchitecture.services;

import com.fiap.hackatonsoftwarearchitecture.repositories.PointRecordRepository;
import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.*;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.EmailService;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointRecordServiceImp implements PointRecordService {

    private final PointRecordRepository repository;
    private final EmailService emailService;

    @Override
    public void register(RecordDTO recordDTO) {
        Record recordEntity = Record.buildEntity(recordDTO);
        log.info("Inserindo novo registro e ponto {}", recordEntity);
        repository.save(recordEntity);
        log.info("Registro de ponto inserido com sucesso {}", recordEntity);
    }

    @Override
    public ReportDailyDTO getReportDailyByEmailAndDate(String email, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        log.info("Consultando relatorio de ponto do dia {}", date);
        var response = generateReportDaily(email, startOfDay, endOfDay);
        log.info("Relatorio de ponto do dia {} consultado com sucesso", date);
        return response;
    }

    @Override
    public ReportMonthlyDTO getReportCurrentMonthly(String email, int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endDate = yearMonth.atEndOfMonth();

        LocalDateTime startOfMonth = startDate.atStartOfDay();
        LocalDateTime endOfMonth = endDate.atTime(23, 59, 59);
        log.info("Consultando relatorio de ponto do mes {}", month);

        var response = generateReportMonthly(email, startOfMonth, endOfMonth);
        log.info("Relatorio de ponto do mes {} consultado com sucesso", month);

        return response;
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

    @Override
    @Async
    public void sendReportEmail(String email, LocalDate date) {
        var reportData = getReportCurrentMonthly(email, date.getMonthValue(), date.getYear());
        emailService.sendEmail(EmailDTO.builder()
                        .subject("Relatório de ponto")
                        .totalHoursWorkedByMonth(reportData.getTotalHoursWorkedByMonth())
                        .recipient(email)
                        .content(reportData.getRecords())
                .build());
    }

    private ReportMonthlyDTO generateReportMonthly(String email, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<Record> records = getRecordsAndSort(email, startOfDay, endOfDay);
        ReportMonthlyDTO reportMonthlyDTO = new ReportMonthlyDTO();
        List<ReportMonthlyDTO.RecordsDetail> recordsList = new ArrayList<>();
        Long totalHoursWorkedByMonth = 0L;

        if (records.isEmpty()) {
            return null;
        }

        for (int day = startOfDay.getDayOfMonth(); day <= endOfDay.getDayOfMonth(); day++) {
            List<Record> recordsByDay = filterRecordByDay(records, day);

            if(!recordsByDay.isEmpty()) {
                ReportDailyDTO dailyReport = calculateReportDaily(recordsByDay);

                List<ReportMonthlyDTO.RecordPoint> recordPoints = new ArrayList<>();
                recordsByDay.stream().forEach(r -> recordPoints.add(ReportMonthlyDTO.buildDTO(r)));

                ReportMonthlyDTO.RecordsDetail recordDetail = new ReportMonthlyDTO.RecordsDetail();
                recordDetail.setRecordPoints(recordPoints);
                recordDetail.setDate(LocalDate.of(startOfDay.getYear(), startOfDay.getMonth(), day));

                recordsList.add(recordDetail);
                totalHoursWorkedByMonth = totalHoursWorkedByMonth + dailyReport.getTotalHoursWorkedByDay();
            }
        }

        reportMonthlyDTO.setRecords(recordsList);
        reportMonthlyDTO.setTotalHoursWorkedByMonth(totalHoursWorkedByMonth);

        return reportMonthlyDTO;
    }

    private List<Record> filterRecordByDay(List<Record> records, int day) {
        return records.stream()
                .filter(r -> r.getDateCreated().getDayOfMonth() == day)
                .collect(Collectors.toList());
    }

    private ReportDailyDTO generateReportDaily(String email, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<Record> records = getRecordsAndSort(email, startOfDay, endOfDay);

        if (records.isEmpty()) {
            return null;
        }

        return calculateReportDaily(records);
    }

    private ReportDailyDTO calculateReportDaily(List<Record> records) {
        ReportDailyDTO dailyReport = new ReportDailyDTO();
        dailyReport.setEntryHour(records.get(0).getDateCreated());
        dailyReport.setExitHour(records.get(records.size() - 1).getDateCreated());

        calculateBreaks(records, dailyReport);
        calculateTotalHoursWorked(dailyReport);
        return dailyReport;
    }

    private List<Record> getRecordsAndSort(String email, LocalDateTime start, LocalDateTime end) {
        List<Record> records = repository.findByEmailAndDateCreatedBetween(email, start, end);

        if (!records.isEmpty()) {
            records.sort(Comparator.comparing(Record::getDateCreated));
        }

        return records;
    }

    private void calculateBreaks(List<Record> records, ReportDailyDTO dailyReport) {
        LocalDateTime startBreak = null;
        List<ReportDailyDTO.Break> breaks = new ArrayList<>();

        for (Record record : records) {
            LocalDateTime dateTime = record.getDateCreated();
            if (startBreak == null) {
                startBreak = dateTime;
            } else {
                breaks.add(new ReportDailyDTO.Break(startBreak, dateTime));
                startBreak = null;
            }
        }
        dailyReport.setBreaks(breaks);
    }

    private void calculateTotalHoursWorked(ReportDailyDTO dailyReport) {
        Duration totalHours = Duration.ZERO;
        for (ReportDailyDTO.Break aBreak : dailyReport.getBreaks()) {
            totalHours = totalHours.plus(Duration.between(aBreak.getStart(), aBreak.getEnd()));
        }
        dailyReport.setTotalHoursWorkedByDay(totalHours.toHours());
    }
}


