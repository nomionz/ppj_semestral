package org.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherHistoryResponse {

    private int cnt;
    private List<WeatherData> list;

    @JsonProperty("cnt")
    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @JsonProperty("list")
    public List<WeatherData> getList() {
        return list;
    }

    public void setList(List<WeatherData> list) {
        this.list = list;
    }

    public static class WeatherData {
        private long dt;
        private Main main;

        @JsonProperty("dt")
        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        @JsonProperty("main")
        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public static class Main {
            private double temp;
            private double pressure;
            private double humidity;

            @JsonProperty("temp")
            public double getTemp() {
                return temp;
            }

            public void setTemp(double temp) {
                this.temp = temp;
            }

            @JsonProperty("pressure")
            public double getPressure() {
                return pressure;
            }

            public void setPressure(double pressure) {
                this.pressure = pressure;
            }

            @JsonProperty("humidity")
            public double getHumidity() {
                return humidity;
            }

            public void setHumidity(double humidity) {
                this.humidity = humidity;
            }
        }
    }
}
