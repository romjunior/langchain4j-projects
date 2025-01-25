package com.estudo.repository.parkingspace;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@RegisterForReflection
@Entity(name = "PARKING_SPACE")
public class ParkingSpace {
    @Id String code;
    ParkingSpaceStatus status;

    public ParkingSpace() {
    }

    public ParkingSpace(String code, ParkingSpaceStatus status) {
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ParkingSpaceStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingSpaceStatus status) {
        this.status = status;
    }
}
