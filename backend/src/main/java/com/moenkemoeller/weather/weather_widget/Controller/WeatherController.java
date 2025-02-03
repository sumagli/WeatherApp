package com.moenkemoeller.weather.weather_widget.Controller;

import com.moenkemoeller.weather.weather_widget.Controller.Interfaces.IWeatherController;
import com.moenkemoeller.weather.weather_widget.Services.WeatherService;
import com.moenkemoeller.weather.weather_widget.Data.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController implements IWeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public ResponseEntity<List<WeatherData>> getWeatherData(
            @PathVariable String city,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(weatherService.getStoredWeatherData(city, startDate, endDate));
    }
}
