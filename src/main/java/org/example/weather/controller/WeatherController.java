package org.example.weather.controller;

import org.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/{cityName}", method = RequestMethod.GET)
    public ResponseEntity<?> getWeather(@PathVariable("cityName") String cityName,
                                        @RequestParam("period") String period) {
        switch (period) {
            case "today":
                return ResponseEntity.ok(weatherService.getWeatherToday(cityName));
            case "week":
                return ResponseEntity.ok(weatherService.getWeatherWeek(cityName));
            case "weeks":
                return ResponseEntity.ok(weatherService.getWeatherTwoWeeks(cityName));
            default:
                return ResponseEntity.badRequest().body("Invalid period specified");
        }
    }
}
