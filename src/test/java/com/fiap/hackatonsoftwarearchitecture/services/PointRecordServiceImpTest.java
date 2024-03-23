package com.fiap.hackatonsoftwarearchitecture.services;

import com.fiap.hackatonsoftwarearchitecture.repositories.PointRecordRepository;
import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordViewDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.ReportDailyDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.ReportMonthlyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PointRecordServiceImpTest {

    @Mock
    private PointRecordRepository repository;

    @InjectMocks
    private PointRecordServiceImp pointRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        RecordDTO recordDTO = new RecordDTO();
        pointRecordService.register(recordDTO);
        verify(repository, times(1)).save(any(Record.class));
    }

    @Test
    void getReportDaily() {
        LocalDate date = LocalDate.of(2024, 3, 23);
        ReportDailyDTO expectedReport = new ReportDailyDTO();
        expectedReport.setTotalHoursWorkedByDay(8L);
        List<Record> recordsMock = getRecordsListMock(23);

        when(repository.findByEmailAndDateCreatedBetween(anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(recordsMock);

        ReportDailyDTO actualReport = pointRecordService.getReportDailyByEmailAndDate("test@test.com", date);
        assertEquals(expectedReport.getTotalHoursWorkedByDay(), actualReport.getTotalHoursWorkedByDay());
    }

    @Test
    void getReportCurrentMonthly() {
        ReportDailyDTO expectedReport = new ReportDailyDTO();
        expectedReport.setTotalHoursWorkedByDay(160L);
        List<Record> recordsMock = getRecordsListMonthMock();

        when(repository.findByEmailAndDateCreatedBetween(anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(recordsMock);

        ReportMonthlyDTO actualReport = pointRecordService.getReportCurrentMonthly("test@test.com", 3, 2024);
        assertEquals(expectedReport.getTotalHoursWorkedByDay(), actualReport.getTotalHoursWorkedByMonth());
    }

    @Test
    void getRecordsByEmailAndDate() {
        List<Record> records = new ArrayList<>();
        records.add(new Record());
        when(repository.findByEmailAndDateCreatedBetween(anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(records);

        List<RecordViewDTO> actualRecords = pointRecordService.getRecordsByEmailAndDate("test@test.com", LocalDate.now());
        assertEquals(1, actualRecords.size());
    }

    private List<Record> getRecordsListMock(int day) {
        List<Record> recordsMock = new ArrayList<>();

        buildReportDaily(recordsMock, 23);

        return recordsMock;
    }

    private Record getRecord(int day, int hour) {
        return getRecord(day, Long.valueOf(day), hour);
    }

    private List<Record> getRecordsListMonthMock() {
        List<Record> recordsMock = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            buildReportDaily(recordsMock, i);
        }

        return recordsMock;
    }

    private Record getRecord(int day, Long id, int hour) {
        return Record.builder()
                .id(id)
                .email("test@test.com")
                .comments("start")
                .dateCreated(LocalDateTime.of(2024, 3, day, hour, 0))
                .build();
    }

    private void buildReportDaily(List<Record> recordsMock, int day) {
        recordsMock.add(getRecord(day, 9));
        recordsMock.add(getRecord(day, 12));
        recordsMock.add(getRecord(day, 13));
        recordsMock.add(getRecord(day, 18));
    }
}
