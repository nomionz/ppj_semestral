package org.example.weather.service;

import org.example.weather.dto.*;
import org.example.weather.entity.CityEntity;
import org.example.weather.entity.WeatherEntity;
import org.example.weather.repository.WeatherMetricsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GeoLocationService geoLocationService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private WeatherMetricsRepository weatherMetricsRepository;

    public DailyWeatherResponse getWeatherToday(String city) {
        Long cityId = getCity(city);
        ZoneId zoneId = ZoneId.systemDefault();

        long startOfDay = LocalDate.now(zoneId)
                .atStartOfDay(zoneId)
                .toEpochSecond() - 1;

        long startOfNextDay = LocalDate.now(zoneId)
                .plusDays(1)
                .atStartOfDay(zoneId)
                .toEpochSecond();

        Optional<DailyWeatherResponse> resp = storeService.findByCityIdAndDatePeriod(cityId, startOfDay, startOfNextDay).stream()
                .map(measurement -> new DailyWeatherResponse(
                        measurement.getDate(),
                        measurement.getTemperature(),
                        measurement.getPressure(),
                        measurement.getHumidity()
                )).findFirst();

        if (resp.isEmpty()) {
            log.error("No weather data found for city {} for today, fetching from the API", city);
            return null;
        }

        return resp.get();
    }

    public WeatherResponse getWeatherWeek(String city) {
        Long cityId = getCity(city);

        ZoneId zoneId = ZoneId.systemDefault();
        long now = LocalDate.now(zoneId)
            .atStartOfDay(zoneId)
            .toEpochSecond();

        long weekLater = LocalDate.now(zoneId)
            .minusWeeks(1)
            .atStartOfDay(zoneId)
            .toEpochSecond();

        return getWeatherResponse(cityId, weekLater, now);
    }

    public WeatherResponse getWeatherTwoWeeks(String city) {
        Long cityId = getCity(city);

        ZoneId zoneId = ZoneId.systemDefault();
        long now = LocalDate.now(zoneId)
            .atStartOfDay(zoneId)
            .toEpochSecond();

        long twoWeeksLater = LocalDate.now(zoneId)
            .minusWeeks(2)
            .atStartOfDay(zoneId)
            .toEpochSecond();

        return getWeatherResponse(cityId, twoWeeksLater, now);
    }

    private WeatherResponse getWeatherResponse(Long cityId, Long from, Long to) {
        WeatherMetrics metrics = weatherMetricsRepository.findWeatherMetricsForPeriod(cityId, from, to);

        List<DailyWeatherResponse> measurements = storeService
                .findByCityIdAndDatePeriod(cityId, from, to).stream()
                .map(measurement -> new DailyWeatherResponse(
                        measurement.getDate(),
                        measurement.getTemperature(),
                        measurement.getPressure(),
                        measurement.getHumidity()
                )).collect(Collectors.toList());

        return new WeatherResponse(metrics, measurements);
    }

    private Long getCity(String city) {
        Optional<CityEntity> cityEntity = storeService.findCityByName(city);
        if (cityEntity.isEmpty()) {
            log.info("City {} not found in the database, fetching from the API", city);
            fetchAndStore(city);
            cityEntity = storeService.findCityByName(city);
        }

        if (cityEntity.isEmpty()) {
            log.error("City not found even after fetch: {}", city);
            return null;
        }

        return cityEntity.get().getId();
    }

    private void fetchAndStore(String cityName) {
        GeoLocationResponse loc = geoLocationService.getGeoLocation(cityName);
        double lat = loc.getLat();
        double lon = loc.getLon();

        ZoneId zoneId = ZoneId.systemDefault();

        long startTs = LocalDate.now(zoneId)
            .minusWeeks(2)
            .atStartOfDay(zoneId)
            .toEpochSecond();

        long endTs = LocalDate.now(zoneId)
            .minusWeeks(1)
            .atStartOfDay(zoneId)
            .toEpochSecond();

        String url = getUrl(lat, lon, startTs, endTs);
        WeatherHistoryResponse resp = restTemplate.getForObject(url, WeatherHistoryResponse.class);
        assert resp != null;

        CityEntity city = new CityEntity();
        city.setName(cityName);
        city.setCountry(loc.getCountry());

        storeCiteMeasurements(city, resp);

        startTs = LocalDate.now(zoneId)
            .minusWeeks(1)
            .atStartOfDay(zoneId)
            .toEpochSecond();

        endTs = LocalDate.now(zoneId)
            .plusDays(1)
            .atStartOfDay(zoneId)
            .toEpochSecond();

        url = getUrl(lat, lon, startTs, endTs);
        resp = restTemplate.getForObject(url, WeatherHistoryResponse.class);
        assert resp != null;

        storeCiteMeasurements(city, resp);
    }

    private void storeCiteMeasurements(CityEntity city, WeatherHistoryResponse resp) {
        List<WeatherEntity> measurements = resp.getList().stream()
                .map(historyData -> {
                    WeatherEntity measurement = new WeatherEntity();
                    measurement.setDate(historyData.getDt());
                    measurement.setTemperature(historyData.getMain().getTemp());
                    measurement.setPressure(historyData.getMain().getPressure());
                    measurement.setHumidity(historyData.getMain().getHumidity());
                    return measurement;
                }).collect(Collectors.toList());

        storeService.saveCityWeather(city, measurements);
    }

    private String getUrl(double lat, double lon, long startTs, long endTs) {
        return "https://history.openweathermap.org/data/2.5/history/city?lat=" + lat + "&lon=" + lon + "&type=hour&units=metric&start=" + startTs + "&end=" + endTs + "&appid=" + apiKey;
    }
}
