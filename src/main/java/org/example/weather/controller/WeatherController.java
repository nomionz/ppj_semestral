package org.example.weather.controller;

import org.example.weather.dto.DailyWeatherResponse;
import org.example.weather.dto.WeatherResponse;
import org.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/today/{cityName}", method = RequestMethod.GET)
    public DailyWeatherResponse getDailyWeather(@PathVariable("cityName") String cityName) {
        return weatherService.getWeatherToday(cityName);
    }

    @RequestMapping(value = "/week/{cityName}", method = RequestMethod.GET)
    public WeatherResponse getWeatherWeek(@PathVariable("cityName") String cityName) {
        return weatherService.getWeatherWeek(cityName);
    }

    @RequestMapping(value = "/weeks/{cityName}", method = RequestMethod.GET)
    public WeatherResponse getWeatherTwoWeeks(@PathVariable("cityName") String cityName) {
        return weatherService.getWeatherTwoWeeks(cityName);
    }
}
