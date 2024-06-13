package com.challenge.pinapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Pinapp Challenge API", version = "${springdoc.version}", description = "Documentation API v1.0")
)
public class PinappApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinappApplication.class, args);
    }

}
