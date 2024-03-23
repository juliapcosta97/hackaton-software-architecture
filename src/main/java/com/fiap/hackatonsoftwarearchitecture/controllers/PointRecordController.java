package com.fiap.hackatonsoftwarearchitecture.controllers;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.DailyReportDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordViewDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/point")
public class PointRecordController {

    @Autowired
    private PointRecordService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@RequestBody RecordDTO record) {
        service.register(record);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/report/daily")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DailyReportDTO> getReportDailyByEmailAndDate(@RequestParam String email,
                                                                        @RequestParam LocalDate date) {
        DailyReportDTO report = service.getReportDailyByEmailAndDate(email, date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/records")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RecordViewDTO>> getRecordsByEmailAndDate(@RequestParam String email,
                                                                @RequestParam LocalDate date) {
        List<RecordViewDTO> records = service.getRecordsByEmailAndDate(email, date);
        return ResponseEntity.ok(records);
    }
}
