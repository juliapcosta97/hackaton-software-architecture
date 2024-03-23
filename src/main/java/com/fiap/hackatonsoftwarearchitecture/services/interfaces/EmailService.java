package com.fiap.hackatonsoftwarearchitecture.services.interfaces;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.EmailDto;

public interface EmailService {

    void sendEmail(EmailDto emailDto);
}
