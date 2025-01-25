package com.estudo.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;

@RegisterForReflection
public record CalculationDTO(
        BigDecimal value,
        long hoursStayed
) {
}
