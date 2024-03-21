package com.fiap.hackatonsoftwarearchitecture.exceptions;

import org.springframework.http.HttpStatus;

public class FailedDependencyException extends BaseHttpException{

    public FailedDependencyException(String message) {
        super(HttpStatus.FAILED_DEPENDENCY, message);
    }
}

