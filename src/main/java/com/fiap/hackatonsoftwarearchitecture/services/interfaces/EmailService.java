package com.fiap.hackatonsoftwarearchitecture.services.interfaces;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.EmailDTO;

public interface EmailService {

    void sendEmail(EmailDTO emailDto);
}
