package com.example.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.SwaggerUiConfigProperties;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Primary
    @Bean
    @ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true", matchIfMissing = true)
    public SwaggerUiConfigProperties swaggerUiConfigProperties() {
        try {
            logger.info("Configuring Swagger UI...");
            SwaggerUiConfigProperties properties = new SwaggerUiConfigProperties();
            
            properties.setPath("/swagger-ui.html");
            properties.setConfigUrl("/swagger-ui/swagger-config");
            properties.setDisplayRequestDuration(true);
            properties.setOperationsSorter("alpha");
            
            Set<SwaggerUrl> urls = new HashSet<>();
            
            urls.add(new SwaggerUrl("Details Service", "/details/api-docs", "Details API"));
            urls.add(new SwaggerUrl("Data Service", "/dataservice/api-docs", "Data Service API"));
            urls.add(new SwaggerUrl("Search Service", "/search/api-docs", "Search API"));
            
            properties.setUrls(urls);
            logger.info("Swagger UI completed，添加了 {} 个服务", urls.size());
            return properties;
        } catch (Exception e) {
            logger.error("Swagger UI wrong: {}", e.getMessage(), e);
            
            SwaggerUiConfigProperties fallback = new SwaggerUiConfigProperties();
            fallback.setPath("/swagger-ui.html");
            return fallback;
        }
    }
}