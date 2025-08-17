package org.estudo.repository.parkingspace;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum ParkingSpaceStatus {
    AVAILABLE, OCCUPIED, DISABLED
}
