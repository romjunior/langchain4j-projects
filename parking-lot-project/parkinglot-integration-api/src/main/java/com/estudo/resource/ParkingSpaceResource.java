package com.estudo.resource;

import com.estudo.repository.parkingspace.ParkingSpace;
import com.estudo.service.ParkingSpaceService;
import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ResponseStatus;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.*;

@Path("/parkingspace")
public class ParkingSpaceResource {

    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceResource(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    @POST
    @ResponseStatus(CREATED)
    public void createParkingSpace(ParkingSpace parkingSpace) {
        Log.info(String.format("M=createParkingSpace code=%s space=%s", parkingSpace.getCode(), parkingSpace.getStatus()));
        parkingSpaceService.createParkingSpace(parkingSpace);
        Log.info("M=createParkingSpace success");
    }

    @GET
    @ResponseStatus(OK)
    public Iterable<ParkingSpace> getAllParkingSpaces() {
        Log.info("M=getAllParkingSpaces");
        return parkingSpaceService.getAllParkingSpaces();
    }

    @GET
    @Path("/{code}")
    @ResponseStatus(OK)
    public Response getParkingSpace(String code) {
        Log.info(String.format("M=getParkingSpace code=%s", code));
        return parkingSpaceService.getParkingSpace(code)
                .map(parkingSpace -> {
                    Log.info(String.format("M=getParkingSpace code=%s sucess", parkingSpace.getCode()));
                    return Response.ok(parkingSpace).build();
                }).orElseGet(() -> {
                    Log.info(String.format("M=getParkingSpace code=%s not found", code));
                    return Response.status(NOT_FOUND).build();
                });
    }
}
