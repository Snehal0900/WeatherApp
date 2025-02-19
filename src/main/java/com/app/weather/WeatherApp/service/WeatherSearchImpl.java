package com.app.weather.WeatherApp.service;

import com.app.weather.WeatherApp.entity.DailyForecast;
import com.app.weather.WeatherApp.entity.HourlyForecast;
import com.app.weather.WeatherApp.entity.WeatherSearch;
import com.app.weather.WeatherApp.repository.WeatherSearchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WeatherSearchImpl implements WeatherSearchService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherSearchRepository repository;

    @Override
    public void saveWeatherSearch(WeatherSearch weather) {
        Optional<WeatherSearch> existingSearch = repository.findTopByCityOrderBySearchedAtDesc(weather.getCity());

        if (existingSearch.isPresent()) {
            WeatherSearch existingWeather = existingSearch.get();
            existingWeather.setTemperature(weather.getTemperature());
            existingWeather.setHumidity(weather.getHumidity());
            existingWeather.setWindSpeed(weather.getWindSpeed());
            existingWeather.setSearchedAt(weather.getSearchedAt());
            repository.save(existingWeather);
        } else {
            repository.save(weather);
        }
    }

    @Override
    public WeatherSearch getWeather(String city) {
        try {
            String url = baseUrl + "/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JSONObject json = new JSONObject(response.getBody());

                // Handle city not found error
                if (json.has("cod") && json.getInt("cod") == 404) {
                    System.err.println("City not found: " + city);
                    return null;
                }

                double temperature = json.getJSONObject("main").getDouble("temp");
                double humidity = json.getJSONObject("main").getDouble("humidity");
                double windSpeed = json.getJSONObject("wind").getDouble("speed");

                return new WeatherSearch(city, temperature, humidity, windSpeed, LocalDateTime.now());
            }
        } catch (Exception e) {
            System.err.println("Error fetching weather: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<DailyForecast> getWeatherForecast(String city) {
        try {
            String url = baseUrl + "/forecast?q=" + city + "&appid=" + apiKey + "&units=metric";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                System.err.println("Error fetching forecast: API request failed.");
                return Collections.emptyList();
            }

            JSONObject json = new JSONObject(response.getBody());

            // Handle city not found
            if (json.has("cod") && json.getInt("cod") == 404) {
                System.err.println("City not found: " + city);
                return Collections.emptyList();
            }

            if (!json.has("list")) {
                throw new JSONException("API response does not contain 'list' field.");
            }

            JSONArray list = json.getJSONArray("list");

            List<DailyForecast> dailyForecasts = new ArrayList<>();
            Set<LocalDate> uniqueDates = new HashSet<>(); // Track unique days
            LocalDate today = LocalDate.now();

            for (int i = 0; i < list.length(); i++) {
                JSONObject entry = list.getJSONObject(i);
                long timestamp = entry.getLong("dt");

                LocalDate date = Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                // Skip today & avoid duplicate days
                if (!date.isEqual(today) && uniqueDates.add(date)) {
                    double temp = entry.getJSONObject("main").getDouble("temp");
                    double humidity = entry.getJSONObject("main").getDouble("humidity");
                    double windSpeed = entry.getJSONObject("wind").getDouble("speed");
                    String description = entry.getJSONArray("weather").getJSONObject(0).getString("description");

                    dailyForecasts.add(new DailyForecast(date, temp, humidity, windSpeed, description));

                    // Stop after collecting 5 distinct days
                    if (dailyForecasts.size() == 5) break;
                }
            }

            return dailyForecasts;
        } 
        catch (Exception e) {
            System.err.println("Error fetching forecast: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<HourlyForecast> getHourlyForecast(String city, LocalDate selectedDate) {
        try {
            String url = baseUrl + "/forecast?q=" + city + "&appid=" + apiKey + "&units=metric";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                System.err.println("Error fetching forecast: API request failed.");
                return Collections.emptyList();
            }

            JSONObject json = new JSONObject(response.getBody());
            JSONArray list = json.getJSONArray("list");

            List<HourlyForecast> hourlyForecasts = new ArrayList<>();
            for (int i = 0; i < list.length(); i++) {
                JSONObject entry = list.getJSONObject(i);
                long timestamp = entry.getLong("dt");

                LocalDateTime dateTime = Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                // If selectedDate is null, default to today
                if (selectedDate == null) {
                    selectedDate = LocalDate.now();
                }

                if (dateTime.toLocalDate().equals(selectedDate)) { // Match selected date
                    double temp = entry.getJSONObject("main").getDouble("temp");
                    double humidity = entry.getJSONObject("main").getDouble("humidity");
                    double windSpeed = entry.getJSONObject("wind").getDouble("speed");
                    String description = entry.getJSONArray("weather").getJSONObject(0).getString("description");

                    hourlyForecasts.add(new HourlyForecast(dateTime.toString(), temp, humidity, windSpeed, description));
                }
            }

            return hourlyForecasts;
        } 
        catch (Exception e) {
            System.err.println("Error fetching hourly forecast: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}