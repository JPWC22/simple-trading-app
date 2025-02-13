package com.trading.platform.simple_trading_app.service;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoPriceAggregatorTest {

    private CryptoPriceAggregator aggregator;

    @BeforeEach
    void setUp() {
        aggregator = new CryptoPriceAggregator();
    }

    @Test
    void shouldAggregatePricesAndSelectBestBidAndAsk() {
        LocalDateTime now = LocalDateTime.now();

        List<CryptoPrice> inputPrices = List.of(
                CryptoPrice.builder()
                        .cryptoPair("BTCUSDT")
                        .bidPrice(BigDecimal.valueOf(40000.00))
                        .askPrice(BigDecimal.valueOf(40500.00))
                        .updatedAt(now.minusMinutes(1))
                        .build(),
                CryptoPrice.builder()
                        .cryptoPair("BTCUSDT")
                        .bidPrice(BigDecimal.valueOf(41000.00))
                        .askPrice(BigDecimal.valueOf(40300.00))
                        .updatedAt(now)
                        .build(),
                CryptoPrice.builder()
                        .cryptoPair("ETHUSDT")
                        .bidPrice(BigDecimal.valueOf(2500.00))
                        .askPrice(BigDecimal.valueOf(2550.00))
                        .updatedAt(now.minusMinutes(1))
                        .build(),
                CryptoPrice.builder()
                        .cryptoPair("ETHUSDT")
                        .bidPrice(BigDecimal.valueOf(2600.00))
                        .askPrice(BigDecimal.valueOf(2530.00))
                        .updatedAt(now)
                        .build()
        );

        List<CryptoPrice> bestPrices = aggregator.aggregate(inputPrices);

        assertEquals(2, bestPrices.size());
        assertEquals(BigDecimal.valueOf(41000.00), bestPrices.stream().filter(p -> p.getCryptoPair().equals("BTCUSDT")).findFirst().get().getBidPrice());
        assertEquals(BigDecimal.valueOf(40300.00), bestPrices.stream().filter(p -> p.getCryptoPair().equals("BTCUSDT")).findFirst().get().getAskPrice());

        assertEquals(BigDecimal.valueOf(2600.00), bestPrices.stream().filter(p -> p.getCryptoPair().equals("ETHUSDT")).findFirst().get().getBidPrice());
        assertEquals(BigDecimal.valueOf(2530.00), bestPrices.stream().filter(p -> p.getCryptoPair().equals("ETHUSDT")).findFirst().get().getAskPrice());
    }

    @Test
    void shouldReturnEmptyListWhenNoPrices() {
        List<CryptoPrice> bestPrices = aggregator.aggregate(List.of());
        assertEquals(0, bestPrices.size());
    }
}