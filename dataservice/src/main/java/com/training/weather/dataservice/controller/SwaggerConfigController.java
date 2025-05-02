package com.training.weather.dataservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SwaggerConfigController {

    @GetMapping(value = "/swagger-ui/swagger-config.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSwaggerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("url", "/v3/api-docs");
        config.put("displayRequestDuration", true);
        config.put("deepLinking", true);
        config.put("operationsSorter", "alpha");
        return config;
    }
}
