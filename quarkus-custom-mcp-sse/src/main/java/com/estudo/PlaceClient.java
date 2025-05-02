package com.estudo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://nominatim.openstreetmap.org")
public interface PlaceClient {

    @GET
    @Path("search")
    List<Place> getPlace(@QueryParam("q") String query, @QueryParam("format") String format);
}
