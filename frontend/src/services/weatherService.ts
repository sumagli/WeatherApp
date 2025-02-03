export interface WeatherData {
  id: number;
  city: string;
  date: string;
  temperature: number;
  cloudCover: number;
  precipitationProbability: number;
}

const API_BASE_URL = "http://localhost:8080/api/weather";

export async function getWeatherData(city: string, startDate: string, endDate: string): Promise<WeatherData[]> {
  const response = await fetch(`${API_BASE_URL}/${encodeURIComponent(city)}?startDate=${startDate}&endDate=${endDate}`);
  if (!response.ok) {
    throw new Error("Failed to fetch weather data");
  }
  const data: WeatherData[] = await response.json();
  return data.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());
}

