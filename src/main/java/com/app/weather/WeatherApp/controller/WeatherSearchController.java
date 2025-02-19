package com.app.weather.WeatherApp.controller;

import com.app.weather.WeatherApp.entity.DailyForecast;
import com.app.weather.WeatherApp.entity.HourlyForecast;
import com.app.weather.WeatherApp.entity.WeatherSearch;
import com.app.weather.WeatherApp.repository.WeatherSearchRepository;
import com.app.weather.WeatherApp.service.WeatherSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WeatherSearchController {
	
	@Autowired
    private WeatherSearchService weatherSearchService;
	
	@Autowired
    private WeatherSearchRepository repository;

	@GetMapping("/weather")
    public String getWeather(@RequestParam(required = false) String city, LocalDate date, Model model) {
        if (city != null && !city.isEmpty()) {
            WeatherSearch weather = weatherSearchService.getWeather(city);
            List<DailyForecast> forecast = weatherSearchService.getWeatherForecast(city);
            List<HourlyForecast> hourlyForecast = weatherSearchService.getHourlyForecast(city, date);

            if (weather == null) {
                model.addAttribute("error", "City not found. Please try again.");
            } 
            else {
                weatherSearchService.saveWeatherSearch(weather);
                
                model.addAttribute("weather", weather);
                model.addAttribute("hourlyForecasts", hourlyForecast);
                model.addAttribute("forecast", forecast);
            }
        }

        List<WeatherSearch> recentSearches = repository.findTop5ByOrderBySearchedAtDesc();
        model.addAttribute("recentSearches", recentSearches);

        return "weather";
    }
	
	@GetMapping("/hourlyForecast")
	public String getHourlyForecast(@RequestParam String city, @RequestParam LocalDate selectedDate, Model model) {
	    List<HourlyForecast> hourlyData = weatherSearchService.getHourlyForecast(city, selectedDate);
	    
	    model.addAttribute("city", city);
	    model.addAttribute("date", selectedDate);
	    model.addAttribute("hourlyForecasts", hourlyData);
	    
	    return "forecast";
	}

}