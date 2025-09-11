package com.sanyao.springaiaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringaiactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringaiactionApplication.class, args);
	}

}
