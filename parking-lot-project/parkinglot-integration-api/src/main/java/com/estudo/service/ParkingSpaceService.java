package com.estudo.service;

import com.estudo.repository.parkingspace.ParkingSpace;
import com.estudo.repository.parkingspace.ParkingSpaceRepository;
import com.estudo.repository.parkingspace.ParkingSpaceStatus;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class ParkingSpaceService {

    ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    @Transactional
    public void createParkingSpace(ParkingSpace parkingSpace) {
        parkingSpaceRepository.persist(parkingSpace);
    }

    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpaceRepository.listAll();
    }

    public Optional<ParkingSpace> getParkingSpace(String code) {
        return parkingSpaceRepository.findByIdOptional(code);
    }

    public boolean isParkingSpaceAvailable(String code) {
        return parkingSpaceRepository.findByIdOptional(code)
                .map(parkingSpace -> parkingSpace.getStatus().equals(ParkingSpaceStatus.AVAILABLE))
                .orElse(false);
    }
}
