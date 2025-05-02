package com.example.search.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Combined result containing data from multiple services")
public class CombinedResult {

    @Schema(description = "Data retrieved from the student service")
    private Object studentData;
    
    @Schema(description = "Port information from the details service")
    private String detailsPort;

    public CombinedResult() {
    }

    public CombinedResult(Object studentData, String detailsPort) {
        this.studentData = studentData;
        this.detailsPort = detailsPort;
    }

    // Getters and Setters
    public Object getStudentData() {
        return studentData;
    }

    public void setStudentData(Object studentData) {
        this.studentData = studentData;
    }

    public String getDetailsPort() {
        return detailsPort;
    }

    public void setDetailsPort(String detailsPort) {
        this.detailsPort = detailsPort;
    }
}