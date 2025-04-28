package com.estudo.custom_mcp_weather_server;

import java.util.List;

public record Weather(Daily daily) {
    public record Daily(List<Double> temperature_2m_mean, List<String> time) {
    }
}
