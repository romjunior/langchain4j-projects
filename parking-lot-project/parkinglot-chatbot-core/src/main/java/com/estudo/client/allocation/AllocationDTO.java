package com.estudo.client.allocation;


import java.time.LocalDateTime;

public record AllocationDTO(
        String parkingSpaceCode,
        String carPlate,
        String carColor,
        LocalDateTime entryDate) {
}
