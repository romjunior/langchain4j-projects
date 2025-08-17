package org.estudo.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import org.estudo.repository.price.Price;
import org.estudo.repository.price.PriceRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

/*    @Transactional
    public void createPrice(InsertPriceDTO price) {
        final var priceEntity = Price.of(price.description(), price.value());
        priceRepository.persist(priceEntity);
    }*/

    public Map<String, BigDecimal> getAllPrices() {
       return priceRepository.listAll()
               .stream().collect(Collectors.toMap(
                       Price::getDescription,
                       Price::getValue
               ));
    }

    public Map<String, BigDecimal> getPricesForCalculation() {
        final var prices = getAllPrices();
        final var firstHourPrice = prices.get("First Hour");
        final var additionalHourPrice = prices.get("Other Hour");

        if (firstHourPrice == null) {
            throw new RuntimeException("First Hour price not found");
        }
        if (additionalHourPrice == null) {
            throw new RuntimeException("Other Hour price not found");
        }

        return Map.of("firstHourPrice", firstHourPrice, "additionalHourPrice", additionalHourPrice);
    }

    @Transactional
    public void deletePrice(UUID id) {
        priceRepository.deleteById(id);
    }
}
