package org.estudo.repository.price;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

@RegisterForReflection
@Entity(name = "PRICE")
public class Price {
    @Id
    private UUID id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "VALUE")
    private BigDecimal value;

    public Price() {
    }

    private Price(UUID id, String description, BigDecimal value) {
        this.id = id;
        this.description = description;
        this.value = value;
    }

    public static Price of(String description, BigDecimal value) {
        return new Price(UUID.randomUUID(), description, value);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
