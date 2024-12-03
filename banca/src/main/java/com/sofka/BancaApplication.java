package com.sofka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class BancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancaApplication.class, args);
	}

}
