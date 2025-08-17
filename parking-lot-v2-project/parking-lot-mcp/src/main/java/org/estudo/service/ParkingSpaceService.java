package org.estudo.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import org.estudo.repository.parkingspace.ParkingSpace;
import org.estudo.repository.parkingspace.ParkingSpaceRepository;
import org.estudo.repository.parkingspace.ParkingSpaceStatus;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class ParkingSpaceService {

    ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    /*@Transactional
    public void createParkingSpace(ParkingSpace parkingSpace) {
        parkingSpaceRepository.persist(parkingSpace);
    }*/

    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpaceRepository.listAll();
    }

    public List<ParkingSpace> getAllAvailableParkingSpaces() {
        return parkingSpaceRepository.listAll()
                .stream()
                .filter(parkingSpace -> parkingSpace.getStatus() == ParkingSpaceStatus.AVAILABLE)
                .toList();
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
