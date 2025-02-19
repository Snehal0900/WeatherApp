package com.app.weather.WeatherApp.repository;

import com.app.weather.WeatherApp.entity.WeatherSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherSearchRepository extends JpaRepository<WeatherSearch, Long> {
	
	Optional<WeatherSearch> findTopByCityOrderBySearchedAtDesc(String city); 
	
	List<WeatherSearch> findTop5ByOrderBySearchedAtDesc();
}