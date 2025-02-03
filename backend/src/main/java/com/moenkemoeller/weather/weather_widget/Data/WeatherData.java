package com.moenkemoeller.weather.weather_widget.Data;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "weather_data")
@Data
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private LocalDate date;

    private double temperature;
    private double cloudCover;
    private double precipitationProbability;
}
