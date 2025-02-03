import React, { useState, useEffect } from "react";
import { getWeatherData, WeatherData } from "../services/weatherService";

const LOCATIONS = ["Stuttgart", "Berlin", "München", "Köln"];

const getWeatherIcon = (cloudCover: number, precipitationProbability: number) => {
  if (precipitationProbability > 50) return "🌧️"; 
  if (cloudCover < 30) return "☀️"; 
  return "☁️"; 
};

const WeatherWidget: React.FC = () => {
  const [selectedCity, setSelectedCity] = useState<string>("Stuttgart");
  const [weatherData, setWeatherData] = useState<WeatherData[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);

      try {
        const today = new Date();
        const fiveDaysInFuture = new Date(today);
        fiveDaysInFuture.setDate(today.getDate() + 5);
        const endDate = fiveDaysInFuture.toISOString().split("T")[0];

        const fiveDaysAgo = new Date(today);
        fiveDaysAgo.setDate(today.getDate() - 5);
        const startDate = fiveDaysAgo.toISOString().split("T")[0];

        const data = await getWeatherData(selectedCity, startDate, endDate);
        setWeatherData(data);
      } catch (err) {
        setError("Failed to fetch weather data.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [selectedCity]); 

  const todayString = new Date().toISOString().split("T")[0];

  return (
    <div className="weather-widget">
      <h2 className="weather-title">Weather in {selectedCity}</h2>
      
      <select className="weather-select" value={selectedCity} onChange={(e) => setSelectedCity(e.target.value)}>
        {LOCATIONS.map((city) => (
          <option key={city} value={city}>{city}</option>
        ))}
      </select>

      {loading && <p className="weather-message">Loading weather data...</p>}
      {error && <p className="weather-message error">Error: {error}</p>}

      {!loading && !error && (
        <div className="weather-forecast">
          {weatherData.map((day) => (
            <div 
              className={`weather-day ${day.date === todayString ? "current-day" : ""}`} 
              key={day.id}
            >
              <span className="weather-date">{day.date}</span>
              <span className="weather-icon">{getWeatherIcon(day.cloudCover, day.precipitationProbability)}</span>
              <span className="weather-temp">{day.temperature.toFixed(1)}°C</span>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default WeatherWidget;
