package com.estudo.repository.parkingspace;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParkingSpaceRepository implements PanacheRepositoryBase<ParkingSpace, String> {
}
