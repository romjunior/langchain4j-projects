package com.estudo.tools;

import com.estudo.client.price.PriceClient;
import com.estudo.client.price.PriceDTO;
import dev.langchain4j.agent.tool.Tool;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class PricingService {

    PriceClient priceClient;

    public PricingService(@RestClient PriceClient priceClient) {
        this.priceClient = priceClient;
    }

    @Tool("Consulta de preços/tarifas")
    public List<PriceDTO> calculatePrice() {
        Log.info("M=calculatePrice");
        final var result = priceClient.getAllPrices();
        Log.info(String.format("M=calculatePrice result=%s", result));
        return result;
    }
}
