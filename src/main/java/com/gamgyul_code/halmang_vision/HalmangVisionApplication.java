package com.gamgyul_code.halmang_vision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HalmangVisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HalmangVisionApplication.class, args);
	}

}
