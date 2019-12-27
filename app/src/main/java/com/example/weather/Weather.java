package com.example.weather;


public class Weather {
    public Weather(String day, String status, String img, String maxTemp, String minTemp) {
        this.day = day;
        this.status = status;
        this.IMG = img;
        MaxTemp = maxTemp;
        MinTemp = minTemp;
    }

    public String day;
    public String status;
    public String IMG;
    public String MaxTemp;
    public String MinTemp;
}
