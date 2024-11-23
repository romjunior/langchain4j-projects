package com.estudo.client.allocation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/allocation")
@RegisterRestClient(configKey = "integration-api")
public interface AllocationClient {
    @POST
    void createAllocation(CreateAllocationDTO allocationDTO);

    @GET
    @Path("/car-plate/{carPlate}")
    AllocationDTO getAllocationByCarPlate(@PathParam("carPlate") String carPlate);
}
