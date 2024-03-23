package com.fiap.hackatonsoftwarearchitecture.services.dtos;

import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;
import lombok.Builder;

import java.util.List;

@Builder
public record EmailDTO(
        String recipient,
        String subject,
        List<ReportMonthlyDTO.RecordsDetail> content,
        Long totalHoursWorkedByMonth

) {
}
