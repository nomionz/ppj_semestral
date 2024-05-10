package org.example.weather.repository;

import org.example.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {
    @Query("SELECT m FROM WeatherEntity m WHERE m.city.id = :cityId AND m.date BETWEEN :startDate AND :endDate")
    List<WeatherEntity> findByCityIdAndDatePeriod(Long cityId, Long startDate, Long endDate);
}
