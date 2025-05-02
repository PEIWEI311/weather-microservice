package com.example.details.controller;

import com.example.details.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Weather Details", description = "Weather information operations")
public class WeatherController {

    private final WeatherService weatherService;

    @Value("${server.port}")
    private int randomServerPort;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = "Get weather details by city", 
               description = "Retrieves weather details for the specified city")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved weather details"),
        @ApiResponse(responseCode = "404", description = "City not found", 
                    content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                    content = @Content)
    })
    @GetMapping("/details")
    public ResponseEntity<?> queryWeatherByCity(
            @Parameter(description = "Name of the city to get weather for", required = true) 
            @RequestParam(required = true) String city) {
        return new ResponseEntity<>(weatherService.findCityIdByName(city), HttpStatus.OK);
    }

    @Operation(summary = "Get service port information", 
               description = "Returns the current port that the service is running on")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved port information")
    @GetMapping("/port")
    public ResponseEntity<?> getPortDirectly() {
        return new ResponseEntity<>("weather service + " + randomServerPort, HttpStatus.OK);
    }
}
