package com.example.weather.config;

import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.weather.service.WeatherService;

@Configuration
public class McpConfig {

    @Bean
    public MethodToolCallbackProvider toolProvider(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder()
            .toolObjects(weatherService)
            .build();
    }
}
