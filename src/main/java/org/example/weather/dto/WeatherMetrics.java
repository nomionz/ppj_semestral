package org.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherMetrics {
    @JsonProperty("average_temperature")
    private double averageTemperature;

    @JsonProperty("average_pressure")
    private double averagePressure;

    @JsonProperty("average_humidity")
    private double averageHumidity;
    
    public WeatherMetrics(double averageTemperature, double averagePressure, double averageHumidity) {
        this.averageTemperature = averageTemperature;
        this.averagePressure = averagePressure;
        this.averageHumidity = averageHumidity;
    }

    // Getters and Setters
    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public double getAveragePressure() {
        return averagePressure;
    }

    public void setAveragePressure(double averagePressure) {
        this.averagePressure = averagePressure;
    }

    public double getAverageHumidity() {
        return averageHumidity;
    }

    public void setAverageHumidity(double averageHumidity) {
        this.averageHumidity = averageHumidity;
    }
}
