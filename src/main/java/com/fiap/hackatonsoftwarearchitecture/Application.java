package com.fiap.hackatonsoftwarearchitecture;

import com.fiap.hackatonsoftwarearchitecture.services.dtos.RecordDTO;
import com.fiap.hackatonsoftwarearchitecture.services.interfaces.PointRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Value("${smtp.email.to}")
	String email;
	@Autowired
	PointRecordService service;

	@Override
	public void run(String... args) throws Exception {
		for (int i = 1; i <= 10; i++) {
			Thread.sleep(2000);
			log.info("Inserindo registro de ponto " + i);
			service.register(new RecordDTO(email, "Comentario " + i));
		}
	}
}
