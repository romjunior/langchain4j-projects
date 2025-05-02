package com.estudo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.open-meteo.com")
public interface WeatherClient {

    @GET
    @Path("/v1/forecast")
    Weather getWeatherByCoordinates(
            @QueryParam("latitude") String lat,
            @QueryParam("longitude") String lon,
            @QueryParam("daily") String daily,
            @QueryParam("timezone") String timezone,
            @QueryParam("temporal_resolution") String temporalResolution
    );
}
