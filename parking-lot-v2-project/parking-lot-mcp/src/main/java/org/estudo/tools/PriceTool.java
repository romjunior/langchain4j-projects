package org.estudo.tools;

import io.quarkiverse.mcp.server.Tool;
import org.estudo.service.PriceService;

public class PriceTool {

    private final PriceService priceService;

    public PriceTool(PriceService priceService) {
        this.priceService = priceService;
    }

    @Tool(description = "Obtem os preços do estacionamento")
    String getPrices() {
        try {
            final var pricesMap = priceService.getPricesForCalculation();
            return "Preços, Primeira Hora: " + pricesMap.get("firstHourPrice") + " Demais Horas: " + pricesMap.get("additionalHourPrice");
        } catch (Exception e) {
            return "Erro ao obter os preços";
        }
    }
}
