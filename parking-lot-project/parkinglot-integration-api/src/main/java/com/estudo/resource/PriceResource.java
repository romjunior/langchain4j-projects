package com.estudo.resource;

import com.estudo.service.PriceService;
import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;
import java.util.UUID;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.*;

@Path("/price")
public class PriceResource {

    private final PriceService  priceService;

    public PriceResource(PriceService priceService) {
        this.priceService = priceService;
    }

    @GET
    @ResponseStatus(OK)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PriceDTO> getPrices() {
        Log.info("M=getPrices");
        return priceService.getAllPrices();
    }

    @POST
    @ResponseStatus(CREATED)
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPrice(InsertPriceDTO insertPriceDTO) {
        Log.info("M=createPrice");
        priceService.createPrice(insertPriceDTO);
        Log.info("M=createPrice success");
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePrice(@PathParam("id") UUID id) {
        Log.info("M=deletePrice");
        priceService.deletePrice(id);
        Log.info("M=deletePrice success");
    }
}
