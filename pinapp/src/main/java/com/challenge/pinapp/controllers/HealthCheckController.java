package com.challenge.pinapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Operation(summary = "checks API health")
    @GetMapping("/health")
    public String healthCheck() {
        return "Application is running!";
    }
}
