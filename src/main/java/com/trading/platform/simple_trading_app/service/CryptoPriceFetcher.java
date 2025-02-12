package com.trading.platform.simple_trading_app.service;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import com.trading.platform.simple_trading_app.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CryptoPriceFetcher {

    private final List<PriceProvider> priceProviders;
    private final CryptoPriceAggregator cryptoPriceAggregator;
    private final CryptoPriceRepository cryptoPriceRepository;

    @Scheduled(fixedRate = 10000)
    public void fetchAndStorePrices() {
        try {
            List<CryptoPrice> allPrices = priceProviders.stream()
                    .flatMap(provider -> provider.fetchPrices().stream())
                    .toList();

            List<CryptoPrice> bestPrices = cryptoPriceAggregator.aggregate(allPrices);

            cryptoPriceRepository.saveAll(bestPrices);
            log.info("Successfully updated database for crypto prices");
        } catch (Exception e) {
                log.error("Unexpected error in fetchAndStorePrices", e);
        }
    }
}

