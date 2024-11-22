package com.estudo.resource;

import com.estudo.repository.price.Price;
import com.estudo.service.PriceService;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    public List<PriceDTO> getPrices() {
        return priceService.getAllPrices();
    }

    @POST
    @ResponseStatus(CREATED)
    public void createPrice(InsertPriceDTO insertPriceDTO) {
        priceService.createPrice(insertPriceDTO);
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePrice(@PathParam("id") UUID id) {
        priceService.deletePrice(id);
    }
}
