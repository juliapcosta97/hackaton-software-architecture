package com.fiap.hackatonsoftwarearchitecture.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {

    @NonNull
    private String email;
    private String comments;
}
