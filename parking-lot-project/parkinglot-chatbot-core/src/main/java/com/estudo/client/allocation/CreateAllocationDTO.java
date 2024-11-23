package com.estudo.client.allocation;

public record CreateAllocationDTO(
        String parkingSpaceCode,
        String carPlate,
        String carColor
) {
}
