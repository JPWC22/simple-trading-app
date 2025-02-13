package com.trading.platform.simple_trading_app.service;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CryptoPriceParserTest {

    private CryptoPriceParser parser;

    @BeforeEach
    void setUp() {
        parser = new CryptoPriceParser();
    }

    private String loadJson(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/" + filename)));
    }

    @Test
    void testParseHuobiResponse() throws IOException {
        String json = loadJson("huobi.json");

        List<CryptoPrice> prices = parser.parse(json, "symbol", "bid", "ask", true);

        assertNotNull(prices);
        assertFalse(prices.isEmpty());
        assertEquals(4, prices.size());

        CryptoPrice btc = prices.stream()
                .filter(p -> p.getCryptoPair().equalsIgnoreCase("BTCUSDT"))
                .findFirst().orElse(null);

        assertNotNull(btc);
        assertEquals(BigDecimal.valueOf(96150.34), btc.getBidPrice());
        assertEquals(BigDecimal.valueOf(96150.35), btc.getAskPrice());
    }

    @Test
    void testParseBinanceResponse() throws IOException {
        String json = loadJson("binance.json");

        List<CryptoPrice> prices = parser.parse(json, "symbol", "bidPrice", "askPrice", false);

        assertNotNull(prices);
        assertFalse(prices.isEmpty());
        assertEquals(4, prices.size());

        CryptoPrice eth = prices.stream()
                .filter(p -> p.getCryptoPair().equalsIgnoreCase("ETHUSDT"))
                .findFirst().orElse(null);

        assertNotNull(eth);
        assertEquals(BigDecimal.valueOf(2615.99), eth.getBidPrice());
        assertEquals(BigDecimal.valueOf(2616.13), eth.getAskPrice());
    }
}

