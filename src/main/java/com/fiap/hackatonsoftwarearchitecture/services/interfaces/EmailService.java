package com.fiap.hackatonsoftwarearchitecture.services.interfaces;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.EmailDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(EmailDTO emailDto);
}
