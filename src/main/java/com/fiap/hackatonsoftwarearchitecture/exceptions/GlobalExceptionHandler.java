package com.fiap.hackatonsoftwarearchitecture.exceptions;

import com.fiap.hackatonsoftwarearchitecture.exceptions.dtos.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseHttpException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpException(BaseHttpException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDTO(e.getMessage()));
    }
}
