package com.estudo;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import org.eclipse.microprofile.rest.client.inject.RestClient;

public class WeatherService {

    private final PlaceClient placeClient;
    private final WeatherClient weatherClient;

    public WeatherService(@RestClient PlaceClient placeClient, @RestClient WeatherClient weatherClient) {
        this.placeClient = placeClient;
        this.weatherClient = weatherClient;
    }

    @Tool(description = "Get the actual temperature by specifing the city name")
    public String getTemperatureByCityName(
            @ToolArg(description = "City name(e.g. Nova York, London)") String cityName
    ) {
        final var places = placeClient.getPlace(cityName, "json");

        if(places == null || places.isEmpty()) {
            return "City not found, ask for the user if the city name is correct";
        }

        final var place = places.getFirst();

        final var weather = this.weatherClient
                .getWeatherByCoordinates(
                        place.lat(),
                        place.lon(),
                        "temperature_2m_mean",
                        "America/Sao_Paulo",
                        "hourly_6"
                );

        if(weather == null || weather.daily() == null || weather.daily().temperature_2m_mean() == null) {
            return "Temperature for the asked city not found";
        }

        return weather.daily().temperature_2m_mean().getFirst() + " Graus Celsius";
    }

}
