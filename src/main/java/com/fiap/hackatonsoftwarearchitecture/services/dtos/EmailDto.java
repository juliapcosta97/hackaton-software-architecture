package com.fiap.hackatonsoftwarearchitecture.services.dtos;

import com.fiap.hackatonsoftwarearchitecture.repositories.entities.Record;

import java.util.List;

public record EmailDto(
        String recipient,
        String subject,
        List<Record> content
) {
}
