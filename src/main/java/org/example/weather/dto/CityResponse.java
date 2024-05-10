package org.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CityResponse {
    @JsonProperty("country")
    private String country;

    @JsonProperty("name")
    private String name;

    public CityResponse(String country, String name) {
        this.country = country;
        this.name = name;
    }
}
