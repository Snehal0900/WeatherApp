# Weather Forecast Application

## Overview
This is a weather forecast web application built with **Spring Boot, Thymeleaf, and OpenWeather API**. It provides real-time weather data for any city, including:
- **Current weather**
- **5-day weather forecast**
- **Hourly weather forecast**
- **Recent searches**

## Features
- Search weather by city
- View hourly forecasts for today and upcoming days
- Check 5-day forecast with scrolling UI
- View recent searches
- Responsive UI with smooth scrolling

## Tech Stack
- **Backend:** Spring Boot, Spring MVC, RestTemplate
- **Frontend:** Thymeleaf, HTML, CSS, JavaScript
- **Database:** MySQL (for storing recent searches)
- **API Integration:** OpenWeather API

## Installation & Setup
### Prerequisites
- Java 21
- Maven
- MySQL (for storing recent searches)
- OpenWeather API key (Register at [OpenWeather](https://openweathermap.org/api))

### Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/weather-app.git
   cd weather-app
   ```
2. Configure **application.properties**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/weather_db
   spring.datasource.username=youruser
   spring.datasource.password=yourpassword
   weather.api.key=YOUR_OPENWEATHER_API_KEY
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Open in browser:
   ```
   http://localhost:8080/weather
   ```

## API Endpoints
- /weather?city={city} |  Get current weather and hourly forecast for a city 
- /hourlyForecast?city={city}&date={yyyy-MM-dd} | Get hourly forecast for a specific date 

## UI Enhancements
- **Dynamic Scroll for Hourly Forecast** 
  - If **less than 6** hourly cards → Centered layout
  - If **more than 6** hourly cards → Scroll enabled
- **FontAwesome Icons** for weather indicators 
