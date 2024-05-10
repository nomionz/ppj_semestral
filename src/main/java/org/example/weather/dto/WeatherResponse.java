package org.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WeatherResponse {
    @JsonProperty("metrics")
    private WeatherMetrics averageMetrics;

    @JsonProperty("daily_weather")
    private List<DailyWeatherResponse> dailyWeather;

    public WeatherResponse(WeatherMetrics averageMetrics, List<DailyWeatherResponse> dailyWeather) {
        this.averageMetrics = averageMetrics;
        this.dailyWeather = dailyWeather;
    }

    // Getters and Setters
    public WeatherMetrics getAverageMetrics() {
        return averageMetrics;
    }

    public void setAverageMetrics(WeatherMetrics averageMetrics) {
        this.averageMetrics = averageMetrics;
    }

    public List<DailyWeatherResponse> getDailyWeather() {
        return dailyWeather;
    }

    public void setDailyWeather(List<DailyWeatherResponse> dailyWeather) {
        this.dailyWeather = dailyWeather;
    }
}

