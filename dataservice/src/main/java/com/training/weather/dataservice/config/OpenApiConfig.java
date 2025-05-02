package com.training.weather.dataservice.config;
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
    public OpenAPI studentDataOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("student dataservice API")
                        .description("student data microservice")
                        .version("v1.0.0"))       
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8200/dataservice").description("Gateway server")
                ));
    }
}