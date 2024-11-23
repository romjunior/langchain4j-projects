package com.estudo.resource;

import com.estudo.service.AllocationService;
import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED;

@Path("/allocation")
public class AllocationResource {

    private final AllocationService allocationService;

    public AllocationResource(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @POST
    public Response createAllocation(AllocationDTO allocationDTO) {
        Log.info(String.format("M=createAllocation alocation=%s", allocationDTO));
        if(this.allocationService.createAllocation(allocationDTO)) {
            Log.info("M=createAllocation success");
            return Response.status(CREATED).build();
        } else {
            Log.info("M=createAllocation error");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/car-plate/{carPlate}")
    public Response getAllocationByCarPlate(@PathParam("carPlate") String carPlate) {
        Log.info(String.format("M=getAllocationByCarPlate carPlate=%s", carPlate));
        final var allocation = this.allocationService.getAllocationByCarPlate(carPlate);
        if(allocation == null) {
            Log.info("M=getAllocationByCarPlate not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Log.info("M=getAllocationByCarPlate success");
        return Response.ok(allocation).build();
    }
}
