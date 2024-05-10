package org.example.weather.repository;

import org.example.weather.entity.CityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepository extends CrudRepository<CityEntity, Long> {
    @Query("SELECT c FROM CityEntity c WHERE c.name = :name")
    Optional<CityEntity> findByName(String name);
}
