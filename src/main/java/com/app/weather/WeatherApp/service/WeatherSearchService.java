package com.app.weather.WeatherApp.service;

import java.time.LocalDate;
import java.util.List;

import com.app.weather.WeatherApp.entity.DailyForecast;
import com.app.weather.WeatherApp.entity.HourlyForecast;
import com.app.weather.WeatherApp.entity.WeatherSearch;


public interface WeatherSearchService {

	public void saveWeatherSearch(WeatherSearch weather);
	
    public WeatherSearch getWeather(String city);
    
    public List<DailyForecast> getWeatherForecast(String city);
    
    public List<HourlyForecast> getHourlyForecast(String city, LocalDate selectedDate);
}