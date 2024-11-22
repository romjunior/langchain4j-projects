package com.estudo.tools;

import com.estudo.client.PriceClient;
import com.estudo.client.PriceDTO;
import dev.langchain4j.agent.tool.Tool;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PricingService {

    PriceClient priceClient;

    public PricingService(@RestClient PriceClient priceClient) {
        this.priceClient = priceClient;
    }

    @RunOnVirtualThread
    @Tool("Consulta de preços/tarifas do estacionamento em reais")
    public List<PriceDTO> calculatePrice() {
        Log.info("M=calculatePrice");
        return priceClient.getAllPrices();
    }
}
