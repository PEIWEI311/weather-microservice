package com.example.details.controller;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@RestController
public class PrometheusController {

    private final PrometheusMeterRegistry registry;

    @Autowired
    public PrometheusController(PrometheusMeterRegistry registry) {
        this.registry = registry;
    }

    @GetMapping("/prometheus-metrics")
    public void getMetrics(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; version=0.0.4; charset=utf-8");
        try (Writer writer = response.getWriter()) {
            writer.write(registry.scrape());
        }
    }
}