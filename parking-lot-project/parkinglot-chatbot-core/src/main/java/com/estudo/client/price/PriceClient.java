package com.estudo.client.price;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/price")
@RegisterRestClient(configKey = "integration-api")
public interface PriceClient {
    @GET
    List<PriceDTO> getAllPrices();
}
