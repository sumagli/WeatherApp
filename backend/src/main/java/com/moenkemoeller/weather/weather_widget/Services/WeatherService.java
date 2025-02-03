package com.moenkemoeller.weather.weather_widget.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moenkemoeller.weather.weather_widget.Data.WeatherData;
import com.moenkemoeller.weather.weather_widget.Repository.IWeatherRepository;
import com.moenkemoeller.weather.weather_widget.Services.Interfaces.IWeatherService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class WeatherService implements IWeatherService {
    private final IWeatherRepository weatherRepository;

    public WeatherService(IWeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    private final String apiUrl = "https://api.open-meteo.com/v1/forecast";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Map<String, double[]> CITIES = new HashMap<>() {{
        put("Stuttgart", new double[]{48.7758, 9.1829});
        put("Berlin", new double[]{52.5200, 13.4050});
        put("München", new double[]{48.1351, 11.5820});
        put("Köln", new double[]{50.9375, 6.9603});
    }};

    @EventListener(ApplicationReadyEvent.class)
    public void initializeWeatherData() {
        fetchAndStoreWeatherDataForAllCities();
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void fetchAndStoreWeatherDataForAllCities() {
        CITIES.forEach(this::fetchAndStoreWeatherData);
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteOldWeatherData() {
        LocalDate cutoffDate = LocalDate.now().minusDays(14);
        weatherRepository.deleteByDateBefore(cutoffDate);
    }

    public void fetchAndStoreWeatherData(String city, double[] coordinates) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(5);
        LocalDate endDate = today.plusDays(5);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("latitude", coordinates[0])
                .queryParam("longitude", coordinates[1])
                .queryParam("daily", "temperature_2m_max,cloudcover_mean,precipitation_probability_mean")
                .queryParam("timezone", "Europe/Berlin")
                .queryParam("start_date", startDate.toString())
                .queryParam("end_date", endDate.toString())
                .toUriString();

        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode daily = root.path("daily");
            List<WeatherData> weatherDataList = new ArrayList<>();

            for (int i = 0; i < daily.path("time").size(); i++) {
                LocalDate date = LocalDate.parse(daily.path("time").get(i).asText());

                Optional<WeatherData> existingData = weatherRepository.findByCityAndDate(city, date);

                if (existingData.isPresent()) {
                    // Update only today's and future data (no need to change historical data)
                    if (!date.isBefore(today)) {
                        WeatherData weatherData = existingData.get();
                        weatherData.setTemperature(daily.path("temperature_2m_max").get(i).asDouble());
                        weatherData.setCloudCover(daily.path("cloudcover_mean").get(i).asDouble());
                        weatherData.setPrecipitationProbability(daily.path("precipitation_probability_mean").get(i).asDouble());

                        weatherRepository.save(weatherData);
                    }
                } else {
                    WeatherData weatherData = new WeatherData();
                    weatherData.setCity(city);
                    weatherData.setDate(date);
                    weatherData.setTemperature(daily.path("temperature_2m_max").get(i).asDouble());
                    weatherData.setCloudCover(daily.path("cloudcover_mean").get(i).asDouble());
                    weatherData.setPrecipitationProbability(daily.path("precipitation_probability_mean").get(i).asDouble());

                    weatherDataList.add(weatherData);
                }
            }

            if (!weatherDataList.isEmpty()) {
                weatherRepository.saveAll(weatherDataList);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data for " + city, e);
        }
    }

    public List<WeatherData> getStoredWeatherData(String city, LocalDate startDate, LocalDate endDate) {
        return weatherRepository.findByCityAndDateBetween(city, startDate, endDate);
    }
}
