package com.example.tema08.configs;

import com.example.tema08.repositories.CalculatorHistory;
import com.example.tema08.rxnetty.HealthCheckResource;
import com.example.tema08.rxnetty.RxNettyHandler;
import com.example.tema08.services.CalculatorService;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static final String HEALTH_URI = "/health";

    @Bean
    public CalculatorHistory calculatorHistory() {
        return new CalculatorHistory();
    }

    @Bean
    public CalculatorService calculatorService() {
        return new CalculatorService(calculatorHistory());
    }

    @Bean
    public HealthCheckResource healthCheckResource() {
        return new HealthCheckResource();
    }

    @Bean
    public HealthCheckEndpoint healthCheckEndpoint() {
        return new HealthCheckEndpoint(healthCheckResource());
    }

    @Bean
    public RxNettyHandler rxNettyHandler() {
        return new RxNettyHandler(HEALTH_URI, healthCheckEndpoint(), healthCheckResource(), calculatorService());
    }
}
