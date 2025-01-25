package com.estudo.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record AllocationDTO(
        String parkingSpaceCode,
        String carPlate,
        String carColor
) {
}
