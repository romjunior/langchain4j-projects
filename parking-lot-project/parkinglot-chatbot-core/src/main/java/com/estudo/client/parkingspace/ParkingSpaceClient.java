package com.estudo.client.parkingspace;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/parkingspace")
@RegisterRestClient(configKey = "integration-api")
public interface ParkingSpaceClient {

    @GET
    List<ParkingSpaceDTO> getAllParkingSpaces();

}
