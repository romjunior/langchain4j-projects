package com.estudo.repository.payment;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum PaymentStatus {
    APPROVED, ALREADY_PAID, NOT_EXISTS
}
