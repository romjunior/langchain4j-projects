package org.estudo.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import org.estudo.repository.allocation.Allocation;
import org.estudo.repository.allocation.AllocationRepository;
import org.estudo.repository.parkingspace.ParkingSpaceStatus;

@RequestScoped
public class AllocationService {

    private final AllocationRepository  allocationRepository;
    private final ParkingSpaceService parkingSpaceService;

    public AllocationService(AllocationRepository allocationRepository, ParkingSpaceService parkingSpaceService) {
        this.allocationRepository = allocationRepository;
        this.parkingSpaceService = parkingSpaceService;
    }

    @Transactional
    public boolean createAllocation(String spaceCode, String carPlate, String carColor) {
        final var isAvailable = parkingSpaceService.isParkingSpaceAvailable(spaceCode);
        if (isAvailable) {
            final var allocation = Allocation.of(
                    spaceCode,
                    carPlate,
                   carColor
            );
            parkingSpaceService.getParkingSpace(spaceCode)
                    .ifPresent(parkingSpace -> parkingSpace.setStatus(ParkingSpaceStatus.OCCUPIED));
            allocationRepository.persist(allocation);
            return true;
        } else {
            return false;
        }
    }

    public Allocation getAllocationByCarPlate(String carPlate) {
        return allocationRepository.findAllocationByCarPlace(carPlate);
    }
}
