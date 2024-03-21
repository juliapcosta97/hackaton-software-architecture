package com.fiap.hackatonsoftwarearchitecture.services.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {

    @NonNull
    @JsonProperty("user_id")
    private Long userId;
    private String comments;
}
