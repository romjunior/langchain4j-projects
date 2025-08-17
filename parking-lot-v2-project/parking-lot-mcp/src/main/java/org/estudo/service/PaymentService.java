package org.estudo.service;

import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import org.estudo.repository.allocation.Allocation;
import org.estudo.repository.parkingspace.ParkingSpaceStatus;
import org.estudo.repository.payment.Payment;
import org.estudo.repository.payment.PaymentRepository;
import org.estudo.repository.payment.PaymentStatus;

import java.math.BigDecimal;
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

    public Tuple2<BigDecimal, Long> calculatePaymentByCarPlate(String carPlate) {
        final var allocation = allocationService.getAllocationByCarPlate(carPlate);
        if(allocation == null) {
            return null;
        }

        final var mapPrices = priceService.getPricesForCalculation();

        final long hoursStayed = getHoursStayed(allocation);

        if(hoursStayed < 1) {
            return Tuple2.of(
                    BigDecimal.valueOf(1).multiply(mapPrices.get("firstHourPrice")),
                    1L
            );
        } else {
            var total = BigDecimal.ZERO;

            total = total.add(mapPrices.get("firstHourPrice").multiply(BigDecimal.ONE));
            total = total.add(mapPrices.get("additionalHourPrice").multiply(BigDecimal.valueOf(hoursStayed - 1)));

            return Tuple2.of(
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

        final var payment = Payment.of(allocation.getId(), calculatePaymentByCarPlate(carPlate).getItem1());

        paymentRepository.persist(payment);
        allocation.setExitDate(LocalDateTime.now());
        parkingSpaceService.getParkingSpace(allocation.getParkingSpaceCode())
                .ifPresent(parkingSpace -> parkingSpace.setStatus(ParkingSpaceStatus.AVAILABLE));
        return PaymentStatus.APPROVED;
    }

    private static long getHoursStayed(Allocation allocation) {
        final var minutesStayed = allocation.getEntryDate().until(LocalDateTime.now(), ChronoUnit.MINUTES);

        return (minutesStayed + 59) / 60;
    }
}
