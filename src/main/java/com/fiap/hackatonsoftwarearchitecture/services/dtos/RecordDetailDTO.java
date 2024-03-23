package com.fiap.hackatonsoftwarearchitecture.services.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDetailDTO {

    @Column(name = "comments", nullable = false, updatable = false)
    private String comments;
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;
}
