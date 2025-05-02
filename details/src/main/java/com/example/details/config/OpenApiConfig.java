package com.example.details.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    

    @Bean
    public OpenAPI weatherDetailsOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Weather Details API")
                        .description("Weather details microservice")
                        .version("v1.0.0"))       
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8200/details").description("Gateway server")
                ));
    }
}