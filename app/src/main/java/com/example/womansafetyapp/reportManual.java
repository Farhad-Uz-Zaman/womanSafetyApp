package com.example.womansafetyapp;

public class reportManual {
    String username,details;
    public reportManual(){

    }

    public reportManual(String useremail, String reportDetails){
        this.username = useremail;
        this.details = reportDetails;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
