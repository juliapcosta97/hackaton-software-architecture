package com.fiap.hackatonsoftwarearchitecture.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

@Configuration
public class ThymeleafTemplateConfig {

    @Bean
    public SpringTemplateEngine reportEmailTemplateEngine() {
        SpringTemplateEngine template = new SpringTemplateEngine();
        template.addTemplateResolver(reportEmailTemplateResolver());
        return template;
    }

    public ClassLoaderTemplateResolver reportEmailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/emails/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(false);
        return resolver;
    }
}
