package com.estudo.tools;

import dev.langchain4j.agent.tool.Tool;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Map;

@ApplicationScoped
public class PricingService {

    @RunOnVirtualThread
    @Tool("Consulta de preços/tarifas do estacionamento em reais")
    public Map<String, BigDecimal> calculatePrice() {
        Log.info("M=calculatePrice");
        return Map.of("firstHour", BigDecimal.valueOf(5),
                "otherHours", BigDecimal.valueOf(2));
    }
}
