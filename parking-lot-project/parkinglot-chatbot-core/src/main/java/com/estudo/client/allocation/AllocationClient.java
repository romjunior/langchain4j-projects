package com.estudo.client.allocation;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/allocation")
@RegisterRestClient(configKey = "integration-api")
public interface AllocationClient {
    @POST
    void createAllocation(AllocationDTO allocationDTO);
}
