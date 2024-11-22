package com.estudo.repository.price;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class PriceRepository implements PanacheRepositoryBase<Price, UUID> {
}
