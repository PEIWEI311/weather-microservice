package com.example.details.controller;

import com.example.details.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Value("${server.port}")
    private int randomServerPort;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/details")
    public ResponseEntity<?> queryWeatherByCity(@RequestParam(required = true) String city) {
        return new ResponseEntity<>(weatherService.findCityIdByName(city), HttpStatus.OK);
    }

    
    
    @GetMapping("/port")
    public ResponseEntity<?> getPortDirectly() {
        return new ResponseEntity<>("weather service + " + randomServerPort, HttpStatus.OK);
    }
}
