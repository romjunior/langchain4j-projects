package com.estudo.tools;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Map;

@ApplicationScoped
public class PricingService {

    @Tool("Consulta de preços/tarifas do estacionamento em reais")
    public Map<String, BigDecimal> calculatePrice(String memoryId, String message) {
        return Map.of("firstHour", BigDecimal.valueOf(5),
                "otherHours", BigDecimal.valueOf(2));
    }
}
