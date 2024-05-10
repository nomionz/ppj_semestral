package org.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyWeatherResponse {
    @JsonProperty("date")
    private long date;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("pressure")
    private double pressure;

    @JsonProperty("humidity")
    private double humidity;

    public DailyWeatherResponse(long date, double temperature, double pressure, double humidity) {
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "DailyWeatherResponse{" +
                "date=" + date +
                "temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}
