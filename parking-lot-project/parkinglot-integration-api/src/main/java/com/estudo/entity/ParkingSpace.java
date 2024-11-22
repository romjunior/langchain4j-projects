package com.estudo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "PARKING_SPACE")
public class ParkingSpace {
    @Id String code;
    Status status;

    public ParkingSpace() {
    }

    public ParkingSpace(String code, Status status) {
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
