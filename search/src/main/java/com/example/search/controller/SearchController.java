package com.example.search.controller;

import com.example.search.model.CombinedResult;
import com.example.search.model.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search/combined")
    public ResponseEntity<GeneralResponse<CombinedResult>> searchCombined(
            @RequestParam(required = false) String studentId) {
        
        try {
            
            CompletableFuture<Object> studentDataFuture = CompletableFuture.supplyAsync(() -> {
                String url = "http://dataservice/student";
                if (studentId != null && !studentId.isEmpty()) {
                    url += "/" + studentId;
                } else {
                    url += "/list";
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
}
