package com.training.weather.dataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DataserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataserviceApplication.class, args);
    }
}
