package com.example.weather;


public class Weather {
    public Weather(String status, double temp, String cityName, double lat, double lon, long dt, String tempmin, String tempmax, String sunset, String sunrise, String pressure,String humidity,String wind,String fells_like) {
        this.status = status;
        this.temp=temp;
        this.cityName=cityName;
        this.lat=lat;
        this.lon=lon;
        this.dt=dt;
        this.tempmin=tempmin;
        this.tempmax=tempmax;
        this.humidity=humidity;
        this.humidity=humidity;
        this.sunrise=sunrise;
        this.sunset=sunset;
        this.pressure=pressure;
        this.wind=wind;
        this.fells_like=fells_like;
    }

    public Weather(String day, String tempmin, String tempmax, String description, String icon) {
        this.day = day;
        this.tempmin = tempmin;
        this.tempmax = tempmax;
        this.description = description;
        this.icon = icon;
    }

    private String status;
    private double temp;
    private String cityName;
    private double lat;
    private double lon;
    private long dt;
    private String tempmin;
    private String tempmax;
    private String sunset;
    private String sunrise;
    private String pressure;
    private String humidity;
    private String wind;
    private String fells_like;
    private String description;
    private String icon;
    private String day;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTempmin() {
        return tempmin;
    }

    public void setTempmin(String tempmin) {
        this.tempmin = tempmin;
    }

    public String getTempmax() {
        return tempmax;
    }

    public void setTempmax(String tempmax) {
        this.tempmax = tempmax;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getFells_like() {
        return fells_like;
    }

    public void setFells_like(String fells_like) {
        this.fells_like = fells_like;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
