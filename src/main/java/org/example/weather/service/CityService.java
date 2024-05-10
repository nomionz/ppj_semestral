package org.example.weather.service;

import org.example.weather.dto.CityResponse;
import org.example.weather.entity.CityEntity;
import org.example.weather.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public void deleteCity(String cityName) {
        Optional<CityEntity> city = cityRepository.findByName(cityName);
        city.ifPresent(cityRepository::delete);
    }

    public Optional<CityEntity> findCityByName(String cityName) {
        return cityRepository.findByName(cityName);
    }

    public void addCity(CityEntity city) {
        cityRepository.save(city);
    }
}
