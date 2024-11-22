package com.estudo;

import com.estudo.entity.ParkingSpace;
import com.estudo.entity.ParkingSpaceRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.ResponseStatus;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.*;

@Path("/parkingspace")
public class ParkingSpaceResource {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceResource(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    @POST
    @ResponseStatus(CREATED)
    @Transactional
    public void createParkingSpace(ParkingSpace parkingSpace) {
        parkingSpaceRepository.persist(parkingSpace);
    }
}
