package com.fiap.hackatonsoftwarearchitecture.repositories.entities;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "record")
@Data
@Builder
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false, updatable = false)
    private String email;
    @Column(name = "comments", nullable = false, updatable = false)
    private String comments;
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    public static Record buildEntity(RecordDTO recordDTO) {
        return Record.builder()
                .email(recordDTO.getEmail())
                .comments(recordDTO.getComments())
                .build();
    }
}
