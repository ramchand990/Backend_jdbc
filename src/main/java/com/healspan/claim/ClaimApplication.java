package com.healspan.claim;

import com.healspan.claim.exception.RestTemplateResponseErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClaimApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaimApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder, RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
		return builder.errorHandler(restTemplateResponseErrorHandler).build();
	}

}
