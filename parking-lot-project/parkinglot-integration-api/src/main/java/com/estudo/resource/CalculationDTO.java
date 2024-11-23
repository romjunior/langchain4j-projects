package com.estudo.resource;

import java.math.BigDecimal;

public record CalculationDTO(
        BigDecimal value,
        long hoursStayed
) {
}
