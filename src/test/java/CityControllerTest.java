import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.example.weather.controller.CityController;
import org.example.weather.entity.CityEntity;
import org.example.weather.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

public class CityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(cityController).build();
    }

    @Test
    public void testGetCity() throws Exception {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName("Paris");
        cityEntity.setCountry("FR");
        when(cityService.findCityByName("Paris")).thenReturn(Optional.of(cityEntity));

        mockMvc.perform(get("/city/Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paris"))
                .andExpect(jsonPath("$.country").value("FR"));

        verify(cityService).findCityByName("Paris");
    }

    @Test
    public void testAddCity() throws Exception {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName("Berlin");
        cityEntity.setCountry("DE");

        mockMvc.perform(post("/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Berlin\",\"country\":\"DE\"}"))
                .andExpect(status().isOk());

        verify(cityService).addCity(any(CityEntity.class));
    }

    @Test
    public void testDeleteCity() throws Exception {
        mockMvc.perform(delete("/city/Berlin"))
                .andExpect(status().isOk());

        verify(cityService).deleteCity("Berlin");
    }


}
