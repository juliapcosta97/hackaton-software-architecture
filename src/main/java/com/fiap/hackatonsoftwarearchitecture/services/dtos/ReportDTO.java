package com.fiap.hackatonsoftwarearchitecture.services.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    @JsonProperty("entry_hour")
    private LocalDateTime entryHour;
    @JsonProperty("exit_hour")
    private LocalDateTime exitHour;
    private List<Break> breaks = new ArrayList<>();
    @JsonProperty("total_hours_worked")
    private Duration totalHoursWorked;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Break {

        private LocalDateTime start;
        private LocalDateTime end;
    }
}
