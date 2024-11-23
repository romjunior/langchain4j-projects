package com.estudo.client.payment;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/payment")
@RegisterRestClient(configKey = "integration-api")
public interface PaymentClient {

    @POST
    @Path("/calculate/{carPlate}")
    CalculationDTO calculatePayment(String carPlate);


    @POST
    @Path("/{carPlate}")
    PaymentStatus pay(String carPlate);
}
