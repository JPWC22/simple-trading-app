package com.trading.platform.simple_trading_app.service.v1;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import com.trading.platform.simple_trading_app.repository.CryptoPriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CryptoPriceService {

    private final CryptoPriceRepository cryptoPriceRepository;

    public CryptoPrice getLatestBestPrice(String symbol) {
        return cryptoPriceRepository.findTopByCryptoPairOrderByUpdatedAtDesc(symbol)
                .orElseThrow(() -> new RuntimeException("Price data not found for symbol: " + symbol));
    }
}
