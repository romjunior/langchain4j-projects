package com.estudo.resource;

import com.estudo.service.AllocationService;
import io.quarkus.logging.Log;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
}
