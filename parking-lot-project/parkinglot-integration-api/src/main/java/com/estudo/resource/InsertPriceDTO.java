package com.estudo.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;

@RegisterForReflection
public record InsertPriceDTO(String description, BigDecimal value) {
}
