package com.estudo.resource;

import com.estudo.repository.payment.PaymentStatus;
import com.estudo.service.PaymentService;
import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/payment")
public class PaymentResource {

    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @POST
    @Path("/calculate/{carPlate}")
    @Produces(MediaType.APPLICATION_JSON)
    public CalculationDTO calculateByCarPlate(@PathParam("carPlate") String carPlate) {
        Log.info(String.format("M=calculateByCarPlate carPlate=%s", carPlate));
        return paymentService.calculatePaymentByCarPlate(carPlate);
    }

    @POST
    @Path("/{carPlate}")
    @Produces(MediaType.APPLICATION_JSON)
    public PaymentStatus payment(@PathParam("carPlate") String carPlate) {
        Log.info(String.format("M=payByCarPlate carPlate=%s", carPlate));
        return paymentService.payment(carPlate);
    }
}
