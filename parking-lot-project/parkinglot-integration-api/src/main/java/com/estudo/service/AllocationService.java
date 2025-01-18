package com.estudo.service;

import com.estudo.repository.allocation.Allocation;
import com.estudo.repository.allocation.AllocationRepository;
import com.estudo.repository.parkingspace.ParkingSpaceStatus;
import com.estudo.resource.AllocationDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

@RequestScoped
public class AllocationService {

    private final AllocationRepository  allocationRepository;
    private final ParkingSpaceService parkingSpaceService;

    public AllocationService(AllocationRepository allocationRepository, ParkingSpaceService parkingSpaceService) {
        this.allocationRepository = allocationRepository;
        this.parkingSpaceService = parkingSpaceService;
    }

    @Transactional
    public boolean createAllocation(AllocationDTO allocationDTO) {
        final var isAvailable = parkingSpaceService.isParkingSpaceAvailable(allocationDTO.parkingSpaceCode());
        if (isAvailable) {
            final var allocation = Allocation.of(
                    allocationDTO.parkingSpaceCode(),
                    allocationDTO.carPlate(),
                    allocationDTO.carColor()
            );
            parkingSpaceService.getParkingSpace(allocationDTO.parkingSpaceCode())
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
