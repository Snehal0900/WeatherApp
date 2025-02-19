package com.app.weather.WeatherApp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weatherSearches")
public class WeatherSearch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private LocalDateTime searchedAt;

    // Constructors
    public WeatherSearch() {}

    public WeatherSearch(String city, double temperature, double humidity, double windSpeed, LocalDateTime searchedAt) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.searchedAt = searchedAt;
    }

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getHumidity() {
		return humidity;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public LocalDateTime getSearchedAt() {
		return searchedAt;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public void setSearchedAt(LocalDateTime searchedAt) {
		this.searchedAt = searchedAt;
	}
}
