import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.example.weather.controller.WeatherController;
import org.example.weather.dto.DailyWeatherResponse;
import org.example.weather.dto.WeatherMetrics;
import org.example.weather.dto.WeatherResponse;
import org.example.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

public class WeatherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(weatherController).build();
    }

    @Test
    public void testGetDailyWeather() throws Exception {
        long date = 1620000000;
        double temp = 25.5;
        double pressure = 1013.25;
        double humidity = 50;
        DailyWeatherResponse response = new DailyWeatherResponse(date,temp, pressure, humidity);
        when(weatherService.getWeatherToday("New York")).thenReturn(response);

        mockMvc.perform(get("/weather/today/New York"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(date))
                .andExpect(jsonPath("$.temperature").value(temp))
                .andExpect(jsonPath("$.pressure").value(pressure))
                .andExpect(jsonPath("$.humidity").value(humidity));

        verify(weatherService).getWeatherToday("New York");
    }

    @Test
    public void testGetWeatherWeek() throws Exception {
        WeatherMetrics metrics = new WeatherMetrics(15, 25, 20);
        WeatherResponse response = getWeatherResponse(metrics);
        when(weatherService.getWeatherWeek("New York")).thenReturn(response);

        mockMvc.perform(get("/weather/week/New York"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metrics.average_temperature").value(15))
                .andExpect(jsonPath("$.metrics.average_pressure").value(25))
                .andExpect(jsonPath("$.metrics.average_humidity").value(20))
                .andExpect(jsonPath("$.daily_weather[0].temperature").value(25.5))
                .andExpect(jsonPath("$.daily_weather[0].pressure").value(1013.25))
                .andExpect(jsonPath("$.daily_weather[0].humidity").value(50));


        verify(weatherService).getWeatherWeek("New York");
    }

    private static WeatherResponse getWeatherResponse(WeatherMetrics metrics) {
        List<DailyWeatherResponse> dailyWeather = List.of(
                new DailyWeatherResponse(1620000000, 25.5, 1013.25, 50)
        );

        return new WeatherResponse(metrics, dailyWeather);
    }

    @Test
    public void testGetWeatherTwoWeeks() throws Exception {
        WeatherMetrics metrics = new WeatherMetrics(22, 25, 20);
        WeatherResponse response = getWeatherResponse(metrics);

        when(weatherService.getWeatherTwoWeeks("Los Angeles")).thenReturn(response);

        mockMvc.perform(get("/weather/weeks/Los Angeles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metrics.average_temperature").value(22))
                .andExpect(jsonPath("$.metrics.average_pressure").value(25))
                .andExpect(jsonPath("$.metrics.average_humidity").value(20))
                .andExpect(jsonPath("$.daily_weather[0].temperature").value(25.5))
                .andExpect(jsonPath("$.daily_weather[0].pressure").value(1013.25))
                .andExpect(jsonPath("$.daily_weather[0].humidity").value(50));

        verify(weatherService).getWeatherTwoWeeks("Los Angeles");
    }
}
