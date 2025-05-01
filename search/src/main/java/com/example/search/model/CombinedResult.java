package com.example.search.model;

public class CombinedResult {
    private Object studentData;
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