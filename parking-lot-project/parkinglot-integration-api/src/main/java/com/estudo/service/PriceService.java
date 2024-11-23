package com.estudo.service;

import com.estudo.repository.price.Price;
import com.estudo.repository.price.PriceRepository;
import com.estudo.resource.InsertPriceDTO;
import com.estudo.resource.PriceDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestScoped
public class PriceService {

    private final PriceRepository  priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Transactional
    public void createPrice(InsertPriceDTO price) {
        final var priceEntity = Price.of(price.description(), price.value());
        priceRepository.persist(priceEntity);
    }

    public List<PriceDTO> getAllPrices() {
        return priceRepository.listAll()
                .stream().map(price -> PriceDTO.of(
                        price.getId(),
                        price.getDescription(),
                        price.getValue()))
                .toList();
    }

    public Map<String, BigDecimal> getPricesForCalculation() {
        final var prices = getAllPrices();
        final var firstHourPrice = prices.stream()
                .filter(price -> price.insertPriceDTO().description().equals("First Hour"))
                .findFirst()
                .map(price -> price.insertPriceDTO().value())
                .orElseThrow(() -> new RuntimeException("First Hour price not found"));
        final var additionalHourPrice = prices.stream()
                .filter(price -> price.insertPriceDTO().description().equals("Other Hours"))
                .findFirst()
                .map(price -> price.insertPriceDTO().value())
                .orElseThrow(() -> new RuntimeException("Other Hours price not found"));
        return Map.of("firstHourPrice", firstHourPrice, "additionalHourPrice", additionalHourPrice);
    }

    @Transactional
    public void deletePrice(UUID id) {
        priceRepository.deleteById(id);
    }
}
