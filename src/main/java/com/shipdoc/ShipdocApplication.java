package com.shipdoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShipdocApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipdocApplication.class, args);
	}

}
