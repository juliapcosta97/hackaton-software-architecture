package com.fiap.hackatonsoftwarearchitecture.services.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {

    @NonNull
    @JsonProperty("email")
    private String email;
    private String comments;
}
