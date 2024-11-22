package com.estudo.entity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParkingSpaceRepository implements PanacheRepository<ParkingSpace> {
}
