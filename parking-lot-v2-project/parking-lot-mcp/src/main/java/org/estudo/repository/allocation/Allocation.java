package org.estudo.repository.allocation;


import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
@Entity(name = "ALLOCATION")
public class Allocation {
    @Id
    private UUID id;
    @Column(name = "PARKINGSPACE_CODE")
    private String parkingSpaceCode;
    @Column(name = "CAR_PLATE")
    private String carPlate;
    @Column(name = "CAR_COLOR")
    private String carColor;
    @Column(name = "ENTRY_DATE")
    private LocalDateTime entryDate;
    @Column(name = "EXIT_DATE")
    private LocalDateTime exitDate;

    public Allocation() {
    }

    private Allocation(UUID id, String parkingSpaceCode, String carPlate, String carColor, LocalDateTime entryDate, LocalDateTime exitDate) {
        this.id = id;
        this.parkingSpaceCode = parkingSpaceCode;
        this.carPlate = carPlate;
        this.carColor = carColor;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public static Allocation of(String parkingSpaceCode, String carPlate, String carColor) {
        return new Allocation(UUID.randomUUID(), parkingSpaceCode, carPlate, carColor, LocalDateTime.now(), null);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParkingSpaceCode() {
        return parkingSpaceCode;
    }

    public void setParkingSpaceCode(String parkingSpaceCode) {
        this.parkingSpaceCode = parkingSpaceCode;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }
}
