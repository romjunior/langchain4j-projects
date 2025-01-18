package com.estudo.service;

import com.estudo.repository.allocation.Allocation;
import com.estudo.repository.parkingspace.ParkingSpaceStatus;
import com.estudo.repository.payment.Payment;
import com.estudo.repository.payment.PaymentRepository;
import com.estudo.repository.payment.PaymentStatus;
import com.estudo.resource.CalculationDTO;
import com.google.common.math.LongMath;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequestScoped
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final AllocationService  allocationService;

    private final PriceService priceService;

    private final ParkingSpaceService parkingSpaceService;

    public PaymentService(PaymentRepository paymentRepository,
                          AllocationService allocationService,
                          PriceService priceService,
                          ParkingSpaceService parkingSpaceService
    ) {
        this.paymentRepository = paymentRepository;
        this.allocationService = allocationService;
        this.priceService = priceService;
        this.parkingSpaceService = parkingSpaceService;
    }

    public CalculationDTO calculatePaymentByCarPlate(String carPlate) {
        final var allocation = allocationService.getAllocationByCarPlate(carPlate);
        if(allocation == null) {
            return null;
        }

        final var mapPrices = priceService.getPricesForCalculation();

        final long hoursStayed = getHoursStayed(allocation);

        if(hoursStayed < 1) {
            return new CalculationDTO(
                    BigDecimal.valueOf(1).multiply(mapPrices.get("firstHourPrice")),
                    1
            );
        } else {
            var total = BigDecimal.ZERO;

            total = total.add(mapPrices.get("firstHourPrice").multiply(BigDecimal.ONE));
            total = total.add(mapPrices.get("additionalHourPrice").multiply(BigDecimal.valueOf(hoursStayed - 1)));

            return new CalculationDTO(
                    total,
                    hoursStayed
            );
        }
    }

    @Transactional
    public PaymentStatus payment(String carPlate) {
        final var allocation = allocationService.getAllocationByCarPlate(carPlate);
        if(allocation == null) {
            return PaymentStatus.NOT_EXISTS;
        } else if(allocation.getExitDate() != null) {
            return PaymentStatus.ALREADY_PAID;
        }

        final var payment = Payment.of(allocation.getId(), calculatePaymentByCarPlate(carPlate).value());

        paymentRepository.persist(payment);
        allocation.setExitDate(LocalDateTime.now());
        parkingSpaceService.getParkingSpace(allocation.getParkingSpaceCode())
                .ifPresent(parkingSpace -> parkingSpace.setStatus(ParkingSpaceStatus.AVAILABLE));
        return PaymentStatus.APPROVED;
    }

    private static long getHoursStayed(Allocation allocation) {
        final var minutesStayed= allocation.getEntryDate().until(LocalDateTime.now(), ChronoUnit.MINUTES);

        return LongMath.divide(minutesStayed, 60, RoundingMode.CEILING);
    }
}
