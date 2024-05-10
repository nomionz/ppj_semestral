package org.example.weather.repository;

import org.example.weather.dto.WeatherMetrics;
import org.example.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherMetricsRepository extends JpaRepository<WeatherEntity, Long> {

    @Query("SELECT new org.example.weather.dto.WeatherMetrics(AVG(m.temperature), AVG(m.pressure), AVG(m.humidity)) " +
            "FROM WeatherEntity m WHERE m.city.id = :cityId AND m.date BETWEEN :startDate AND :endDate")
    WeatherMetrics findWeatherMetricsForPeriod(Long cityId, Long startDate, Long endDate);
}

