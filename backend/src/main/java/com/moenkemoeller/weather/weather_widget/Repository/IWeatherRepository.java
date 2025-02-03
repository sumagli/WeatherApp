package com.moenkemoeller.weather.weather_widget.Repository;

import com.moenkemoeller.weather.weather_widget.Data.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IWeatherRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByCityAndDate(String city, LocalDate date);
    void deleteByDateBefore(LocalDate cutoffDate);
    List<WeatherData> findByCityAndDateBetween(String city, LocalDate start, LocalDate end);
}
