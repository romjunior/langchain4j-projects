package com.estudo.resource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.math.BigDecimal;
import java.util.UUID;

public record PriceDTO(@JsonUnwrapped InsertPriceDTO insertPriceDTO, UUID id) {

    public static PriceDTO of(UUID id, String description, BigDecimal value) {
        return new PriceDTO(new InsertPriceDTO(description, value), id);
    }
}
