package com.ad3bay0.rkots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Rkots {

	public static void main(String[] args) {
		SpringApplication.run(Rkots.class, args);
	}

}
