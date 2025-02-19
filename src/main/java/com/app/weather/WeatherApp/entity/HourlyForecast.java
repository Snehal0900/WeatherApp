package com.app.weather.WeatherApp.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HourlyForecast {
	
    private LocalDateTime dateTime;
    
    private double temperature;
    
    private double humidity;
    
    private double windSpeed;
    
    private String weatherDescription;

    // Constructor
    public HourlyForecast(String dateTime, double temperature, double humidity, double windSpeed, String weatherDescription) {
        this.dateTime = LocalDateTime.parse(dateTime);
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weatherDescription = weatherDescription;
    }

    // Getters and Setters
    public String getFormattedTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
