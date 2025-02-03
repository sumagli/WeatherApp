package com.moenkemoeller.weather.weather_widget.Controller.Interfaces;

import com.moenkemoeller.weather.weather_widget.Data.WeatherData;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IWeatherController {
    ResponseEntity<List<WeatherData>> getWeatherData(String city, LocalDate startDate, LocalDate endDate);
}
