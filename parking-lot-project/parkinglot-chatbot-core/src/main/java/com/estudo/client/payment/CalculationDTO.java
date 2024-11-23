package com.estudo.client.payment;

import java.math.BigDecimal;

public record CalculationDTO(
        BigDecimal value,
        long hoursStayed
) {
}
