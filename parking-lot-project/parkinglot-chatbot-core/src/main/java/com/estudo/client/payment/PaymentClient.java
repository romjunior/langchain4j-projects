package com.estudo.client.payment;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/payment")
@RegisterRestClient(configKey = "integration-api")
public interface PaymentClient {

    @GET
    @Path("/calculate/{carPlate}")
    CalculationDTO calculatePayment(String carPlate);


    @GET
    @Path("/{carPlate}")
    PaymentStatus pay(String carPlate);
}
