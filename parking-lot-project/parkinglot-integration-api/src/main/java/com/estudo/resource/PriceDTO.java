package com.estudo.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.util.UUID;

@RegisterForReflection
public record PriceDTO(@JsonUnwrapped InsertPriceDTO insertPriceDTO, UUID id) {

    public static PriceDTO of(UUID id, String description, BigDecimal value) {
        return new PriceDTO(new InsertPriceDTO(description, value), id);
    }
}
