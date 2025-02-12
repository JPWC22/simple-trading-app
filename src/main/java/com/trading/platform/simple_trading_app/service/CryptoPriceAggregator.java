package com.trading.platform.simple_trading_app.service;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CryptoPriceAggregator {

    public List<CryptoPrice> aggregate(List<CryptoPrice> prices) {
        try {
            if (prices.isEmpty()) {
                log.warn("Received an empty or null price list. Returning empty result.");
                return List.of();
            }

            return prices.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(
                                    CryptoPrice::getCryptoPair,
                                    Function.identity(),
                                    this::getBestPrice
                            ),
                            map -> List.copyOf(map.values())
                    ));
        } catch (Exception e) {
            log.error("Error while aggregating crypto prices", e);
            return List.of();
        }
    }

    private CryptoPrice getBestPrice(CryptoPrice existing, CryptoPrice newEntry) {
        return CryptoPrice.builder()
                .cryptoPair(existing.getCryptoPair())
                .bidPrice(Math.max(existing.getBidPrice(), newEntry.getBidPrice()))
                .askPrice(Math.min(existing.getAskPrice(), newEntry.getAskPrice()))
                .updatedAt(existing.getUpdatedAt().isAfter(newEntry.getUpdatedAt()) ? existing.getUpdatedAt() : newEntry.getUpdatedAt())
                .build();
    }
}
