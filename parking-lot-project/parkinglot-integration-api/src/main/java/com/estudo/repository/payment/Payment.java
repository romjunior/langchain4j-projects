package com.estudo.repository.payment;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
@Entity(name = "PAYMENT")
public class Payment {
    @Id
    private UUID id;
    @Column(name = "ALLOCATION_ID")
    private UUID allocationId;
    @Column(name = "VALUE", precision = 10, scale = 2)
    private BigDecimal value;
    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    public Payment() {
    }

    private Payment(UUID id, UUID allocationId, BigDecimal value, LocalDateTime paymentDate) {
        this.id = id;
        this.allocationId = allocationId;
        this.value = value;
        this.paymentDate = paymentDate;
    }

    public static Payment of(UUID allocationId, BigDecimal value) {
        return new Payment(UUID.randomUUID(), allocationId, value, LocalDateTime.now());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(UUID allocationId) {
        this.allocationId = allocationId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
