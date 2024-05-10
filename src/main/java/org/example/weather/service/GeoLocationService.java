package org.example.weather.service;

import org.example.weather.dto.GeoLocationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoLocationService {
    private static final Logger log = LoggerFactory.getLogger(GeoLocationService.class);
    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public GeoLocationResponse getGeoLocation(String city) {
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + apiKey;
        log.info("Sending request to: {}", url);
        GeoLocationResponse[] responses = restTemplate.getForObject(url, GeoLocationResponse[].class);
        if (responses != null && responses.length > 0) {
            log.info("Found location: {}", (Object) responses);
            return responses[0]; // return the first location if available
        }
        return null; // or throw an exception, depending on your error handling strategy
    }
}
//http://api.openweathermap.org/geo/1.0/direct?q=Prague&limit=1&appid=586981415baf7a6413e0261aca33159c

