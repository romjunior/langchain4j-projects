package com.estudo.custom_mcp_weather_server;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class WeatherService {

    private final RestClient restGeolocationClient;

    private final RestClient restWeatherClient;

    public WeatherService() {
        //https://nominatim.openstreetmap.org/search?q=Franco%20da%20Rocha&format=json
        this.restGeolocationClient = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36")
                .build();

        //https://api.open-meteo.com/v1/forecast?latitude=-23.33&longitude=-46.72&daily=temperature_2m_mean&timezone=America%2FSao_Paulo&temporal_resolution=hourly_6
        this.restWeatherClient = RestClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36")
                .build();
    }

    @Tool(description = "Get the actual temperature by specifing the city name")
    public String getTemperatureByCityName(
            @ToolParam(description = "City name(e.g. Nova York, London)") String cityName
    ) {
        final List<Place> places = this.restGeolocationClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", cityName)
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (places == null || places.isEmpty()) {
            return "City not found";
        }

        final Place place = places.getFirst();

        final Weather weather = this.restWeatherClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", place.lat())
                        .queryParam("longitude", place.lon())
                        .queryParam("daily", "temperature_2m_mean")
                        .queryParam("timezone", "America/Sao_Paulo")
                        .queryParam("temporal_resolution", "hourly_6")
                        .build())
                .retrieve()
                .body(Weather.class);

        if (weather == null || weather.daily() == null || weather.daily().temperature_2m_mean() == null) {
            return "Weather not found";
        }

        return weather.daily().temperature_2m_mean().getFirst() + " Graus Cesius";
    }
}
