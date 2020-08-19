package com.example.womansafetyapp;

public class reprtBug {
    String subject,details;
    public reprtBug(){

    }

    public reprtBug(String subject, String reportDetails){
        this.subject = subject;
        this.details = reportDetails;

    }

    public String getSub() {
        return subject;
    }

    public void setSub(String username) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

