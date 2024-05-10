package org.example.weather.service;

import org.example.weather.entity.CityEntity;
import org.example.weather.entity.WeatherEntity;
import org.example.weather.repository.CityRepository;
import org.example.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    private static final Logger log = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private WeatherRepository weatherRepository;

    @Transactional
    public void saveCityWeather(CityEntity city, List<WeatherEntity> weathers) {
        CityEntity savedCity = cityRepository.save(city);

        weathers.forEach(weather -> {
            weather.setCity(savedCity);
            weatherRepository.save(weather);
        });
    }

    public List<WeatherEntity> findByCityIdAndDatePeriod(Long cityId, Long startDate, Long endDate) {
        log.info("Finding weather for city {} between {} and {}", cityId, startDate, endDate);
        return weatherRepository.findByCityIdAndDatePeriod(cityId, startDate, endDate);
    }

    public Optional<CityEntity> findCityByName(String name) {
        log.info("Finding city by name: {}", name);
        return cityRepository.findByName(name);
    }
}
