package com.fiap.hackatonsoftwarearchitecture.services.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportMonthlyDTO {

    @JsonProperty("records")
    private List<RecordsDetail> records;
    @JsonProperty("total_hours_worked_by_month")
    private Long totalHoursWorkedByMonth;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecordsDetail {

        private LocalDate date;
        @JsonProperty("record_points")
        private List<RecordPoint> recordPoints;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecordPoint {
        private String email;
        private String comments;
        @JsonProperty("record_time")
        private LocalDateTime recordTime;
    }

    @JsonIgnore
    public static RecordPoint buildDTO(Record record) {
        return RecordPoint.builder()
                .email(record.getEmail())
                .comments(record.getComments())
                .recordTime(record.getDateCreated())
                .build();
    }
}
