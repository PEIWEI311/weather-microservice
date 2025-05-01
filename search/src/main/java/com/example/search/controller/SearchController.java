package com.example.search.controller;

import com.example.search.model.CombinedResult;
import com.example.search.model.GeneralResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search/combined")
    @HystrixCommand(fallbackMethod = "searchCombinedFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public ResponseEntity<GeneralResponse<CombinedResult>> searchCombined(
            @RequestParam(required = false) String major) {
        
        try {
            
            CompletableFuture<Object> studentDataFuture = CompletableFuture.supplyAsync(() -> {
                String url;
                if (major != null && !major.isEmpty()) {
                    url = "http://dataservice/student/major/" + major;
                } else {
                    url = "http://dataservice/student";
                }
                return restTemplate.getForObject(url, Object.class);
            });

            CompletableFuture<String> detailsPortFuture = CompletableFuture.supplyAsync(() -> {
                return restTemplate.getForObject("http://details/port", String.class);
            });

            
            CompletableFuture.allOf(studentDataFuture, detailsPortFuture).join();

            
            Object studentData = studentDataFuture.get();
            String detailsPort = detailsPortFuture.get();

            
            CombinedResult result = new CombinedResult(studentData, detailsPort);
            
            
            GeneralResponse<CombinedResult> response = new GeneralResponse<>(
                    HttpStatus.OK.value(), result);

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (InterruptedException | ExecutionException e) {
            GeneralResponse<CombinedResult> errorResponse = new GeneralResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    public ResponseEntity<GeneralResponse<CombinedResult>> searchCombinedFallback(String major) {
        
        Map<String, String> defaultStudentData = new HashMap<>();
        defaultStudentData.put("message", "Student data service is currently unavailable");
        
        String defaultDetailsPort = "Details service is currently unavailable";
        
        CombinedResult fallbackResult = new CombinedResult(defaultStudentData, defaultDetailsPort);
        
        GeneralResponse<CombinedResult> response = new GeneralResponse<>(
                HttpStatus.SERVICE_UNAVAILABLE.value(), fallbackResult);
        
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
