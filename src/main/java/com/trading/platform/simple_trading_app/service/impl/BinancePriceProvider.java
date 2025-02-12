package com.trading.platform.simple_trading_app.service.impl;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import com.trading.platform.simple_trading_app.enums.SupportedCryptos;
import com.trading.platform.simple_trading_app.service.CryptoPriceParser;
import com.trading.platform.simple_trading_app.service.PriceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BinancePriceProvider implements PriceProvider {

    private final RestTemplate restTemplate;
    private final CryptoPriceParser cryptoPriceParser;

    private static final String BINANCE_URL = "https://api.binance.com/api/v3/ticker/bookTicker";

    @Override
    public List<CryptoPrice> fetchPrices() {
        ResponseEntity<String> response = restTemplate.getForEntity(BINANCE_URL, String.class);
        return cryptoPriceParser.parse(response.getBody(), "symbol", "bidPrice", "askPrice", false)
                .stream()
                .filter(price -> SupportedCryptos.isSupported(price.getCryptoPair()))
                .toList();
    }
}
