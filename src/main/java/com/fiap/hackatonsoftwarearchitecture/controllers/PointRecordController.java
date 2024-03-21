package com.fiap.hackatonsoftwarearchitecture.controllers;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.dtos.ResponseDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/point")
public class PointRecordController {

    @Autowired
    private PointRecordService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RecordDTO record) {
        service.register(record);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/records")
    public ResponseEntity<List<RecordDTO>> getRecordsByUserId(@RequestParam Long userId,
                                                                        @RequestParam LocalDateTime start,
                                                                        @RequestParam LocalDateTime end) {
        List<RecordDTO> records = service.getRecordByUserId(userId, start, end);
        return ResponseEntity.ok(records);
    }
}
