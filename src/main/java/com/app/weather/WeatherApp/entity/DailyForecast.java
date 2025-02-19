package com.app.weather.WeatherApp.entity;

import java.time.LocalDate;

public class DailyForecast {
	
	private LocalDate date;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private String weatherDescription;
    
    //Constructor
    public DailyForecast() {}
    
	public DailyForecast(LocalDate date, double temperature, double humidity, double windSpeed, String weatherDescription) {
		this.date = date;
		this.temperature = temperature;
		this.humidity = humidity;
		this.windSpeed = windSpeed;
		this.weatherDescription = weatherDescription;
	}

	//Getter and Setters
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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
