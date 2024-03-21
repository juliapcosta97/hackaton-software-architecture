package com.fiap.hackatonsoftwarearchitecture.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tech-challenge/health")
@Slf4j
public class HealthController {

    @GetMapping
    public Map<String, String> healthCheck() {
        var response = Map.of("status", "OK");
        log.info("Endpoint de Health: {}", response);
        return response;
    }

}
