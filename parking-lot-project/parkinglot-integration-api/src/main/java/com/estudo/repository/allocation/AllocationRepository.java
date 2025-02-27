package com.estudo.repository.allocation;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AllocationRepository implements PanacheRepositoryBase<Allocation, UUID> {

    public Allocation findAllocationByCarPlace(String carPlate) {
        return find("carPlate", carPlate).firstResult();
    }
}
