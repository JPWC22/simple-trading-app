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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HuobiPriceProviderTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CryptoPriceParser cryptoPriceParser;

    @InjectMocks
    private HuobiPriceProvider huobiPriceProvider;

    private String huobiJson;

    @BeforeEach
    void setUp() throws IOException {
        huobiJson = new String(Files.readAllBytes(Paths.get("src/test/resources/huobi.json")));
    }

    @Test
    void fetchPrices_shouldReturnFilteredPrices() {
        when(restTemplate.getForEntity("https://api.huobi.pro/market/tickers", String.class))
                .thenReturn(ResponseEntity.ok(huobiJson));

        List<CryptoPrice> mockPrices = List.of(
                CryptoPrice.builder().cryptoPair("BTCUSDT").bidPrice(40000.00).askPrice(40500.00).build(),
                CryptoPrice.builder().cryptoPair("ETHUSDT").bidPrice(2500.00).askPrice(2550.00).build()
        );

        when(cryptoPriceParser.parse(huobiJson, "symbol", "bid", "ask", true))
                .thenReturn(mockPrices);

        List<CryptoPrice> prices = huobiPriceProvider.fetchPrices();

        assertEquals(2, prices.size());
        assertTrue(prices.stream().allMatch(p -> p.getCryptoPair().equals("BTCUSDT") || p.getCryptoPair().equals("ETHUSDT")));
    }
}

