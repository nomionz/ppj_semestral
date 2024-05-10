package org.example.weather.controller;

import org.example.weather.dto.CityResponse;
import org.example.weather.entity.CityEntity;
import org.example.weather.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/{cityName}", method = RequestMethod.GET)
    public CityResponse getCity(@PathVariable("cityName") String cityName) {
        Optional<CityEntity> city = cityService.findCityByName(cityName);
        return city.map(cityEntity -> new CityResponse(cityEntity.getCountry(), cityEntity.getName())).orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCity(@RequestBody CityEntity city) {
        cityService.addCity(city);
    }

    @RequestMapping(value = "/{cityName}", method = RequestMethod.DELETE)
    public void deleteCity(@PathVariable String cityName) {
        cityService.deleteCity(cityName);
    }
}
