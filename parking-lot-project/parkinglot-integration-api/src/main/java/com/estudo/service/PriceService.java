package com.estudo.service;

import com.estudo.repository.price.Price;
import com.estudo.repository.price.PriceRepository;
import com.estudo.resource.InsertPriceDTO;
import com.estudo.resource.PriceDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

import java.util.List;
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

    @Transactional
    public void deletePrice(UUID id) {
        priceRepository.deleteById(id);
    }
}
