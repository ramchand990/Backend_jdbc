package com.healspan.claim.client.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.healspan.claim"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(getApiInfo());
    }
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "HealSpan Claim Service",
                "Message",
                "0.0.1-SNAPSHOT",
                "Message",
                new Contact("HEALSPAN","http://localhost:8109","Email_ID"),
                "Copyright 2023-2024",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}
