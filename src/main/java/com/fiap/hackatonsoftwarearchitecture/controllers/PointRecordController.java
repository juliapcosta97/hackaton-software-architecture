package com.fiap.hackatonsoftwarearchitecture.controllers;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.ReportDailyDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordViewDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.ReportMonthlyDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/point")
public class PointRecordController {

    @Autowired
    private PointRecordService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RecordDTO record) {
        service.register(record);
    }

    @GetMapping("/report/daily")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReportDailyDTO> getReportDailyByEmailAndDate(@RequestParam("email") String email,
                                                                       @RequestParam("date") LocalDate date) {
        ReportDailyDTO report = service.getReportDailyByEmailAndDate(email, date);

        if(isNull(report)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(report);
    }

    @GetMapping("/report/monthly")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReportMonthlyDTO> getReportMonthlyByEmailAndDate(@RequestParam("email") String email,
                                                                           @RequestParam("date") LocalDate date) {
        ReportMonthlyDTO report = service.getReportCurrentMonthly(email, date.getMonth().getValue(), date.getYear());

        if(isNull(report)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(report);
    }

    @GetMapping("/records")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RecordViewDTO>> getRecordsByEmailAndDate(@RequestParam("email") String email,
                                                                        @RequestParam("date") LocalDate date) {
        List<RecordViewDTO> records = service.getRecordsByEmailAndDate(email, date);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/report/monthly/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendEmailReportMonthlyByEmailAndDate(@RequestParam("email") String email,
                                                     @RequestParam("date") LocalDate date) {
        service.sendReportEmail(email, date);
    }
}
