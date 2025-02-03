package com.moenkemoeller.weather.weather_widget.Services.Interfaces;

import com.moenkemoeller.weather.weather_widget.Data.WeatherData;

import java.time.LocalDate;
import java.util.List;

public interface IWeatherService {
    void fetchAndStoreWeatherDataForAllCities();
    void deleteOldWeatherData();
    List<WeatherData> getStoredWeatherData(String city, LocalDate startDate, LocalDate endDate);
}

