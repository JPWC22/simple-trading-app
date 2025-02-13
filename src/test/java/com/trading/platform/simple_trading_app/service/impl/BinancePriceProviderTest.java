package com.trading.platform.simple_trading_app.service.impl;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import com.trading.platform.simple_trading_app.service.CryptoPriceParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BinancePriceProviderTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CryptoPriceParser cryptoPriceParser;

    @InjectMocks
    private BinancePriceProvider binancePriceProvider;

    private String binanceJson;

    @BeforeEach
    void setUp() throws IOException {
        binanceJson = new String(Files.readAllBytes(Paths.get("src/test/resources/binance.json")));
    }

    @Test
    void fetchPrices_shouldReturnFilteredPrices() {
        when(restTemplate.getForEntity("https://api.binance.com/api/v3/ticker/bookTicker", String.class))
                .thenReturn(ResponseEntity.ok(binanceJson));

        List<CryptoPrice> mockPrices = List.of(
                CryptoPrice.builder().cryptoPair("BTCUSDT").bidPrice(BigDecimal.valueOf(40000.00))
                        .askPrice(BigDecimal.valueOf(40500.00)).build(),
                CryptoPrice.builder().cryptoPair("ETHUSDT").bidPrice(BigDecimal.valueOf(2500.00))
                        .askPrice(BigDecimal.valueOf(2550.00)).build()
        );

        when(cryptoPriceParser.parse(binanceJson, "symbol", "bidPrice", "askPrice", false))
                .thenReturn(mockPrices);

        List<CryptoPrice> prices = binancePriceProvider.fetchPrices();

        assertEquals(2, prices.size());
        assertTrue(prices.stream().allMatch(p -> p.getCryptoPair().equals("BTCUSDT") || p.getCryptoPair().equals("ETHUSDT")));
    }
}
