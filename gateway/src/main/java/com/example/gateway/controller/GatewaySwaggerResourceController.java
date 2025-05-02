package com.example.gateway.controller;

import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GatewaySwaggerResourceController {

    @Autowired
    private SwaggerUiConfigProperties swaggerUiConfigProperties;

    @GetMapping(value = "/swagger-ui/swagger-config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSwaggerConfig() {
        Map<String, Object> config = new HashMap<>();
        
        // Only include properties that exist in the SwaggerUiConfigProperties class
        config.put("urls", swaggerUiConfigProperties.getUrls());
        
       
        config.put("displayRequestDuration", true);
        config.put("deepLinking", true);
        config.put("operationsSorter", "alpha");
        
        return config;
    }
}