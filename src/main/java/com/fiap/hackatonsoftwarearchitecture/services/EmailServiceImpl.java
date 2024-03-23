package com.fiap.hackatonsoftwarearchitecture.services;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.EmailDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine engine;
    @Value("${smtp.email.from}")
    private String from;
    @Value("${smtp.email.template}")
    private String templatePath;

    @Override
    @Async
    public void sendEmail(EmailDTO emailDto) {
        log.info("Enviando email com assunto {} para {}", emailDto.subject(),emailDto.recipient());

        try{
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            helper.setTo(emailDto.recipient());
            helper.setSubject(emailDto.subject());
            helper.setText(createBodyAsHtml(emailDto), true);

            mailSender.send(mimeMessage);

            log.info("Email com assunto {} enviado para {}", emailDto.subject(),emailDto.recipient());
        }catch (Exception e) {
            log.error("Erro ao enviar email com assunto {} para {} erro {}", emailDto.subject(),emailDto.recipient(), e.getMessage());
        }
    }

    private String createBodyAsHtml(EmailDTO emailDto) {
        var ctx = new Context(new Locale("pt", "BR"),
                Map.of("records", emailDto.content(),
                        "user", emailDto.recipient(),
                        "totalHoursWorkedByMonth", emailDto.totalHoursWorkedByMonth()
                ));
        return engine.process(templatePath, ctx);
    }
}
