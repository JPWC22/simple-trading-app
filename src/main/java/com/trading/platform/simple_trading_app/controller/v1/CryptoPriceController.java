package com.trading.platform.simple_trading_app.controller.v1;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import com.trading.platform.simple_trading_app.service.v1.Impl.CryptoPriceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/crypto")
public class CryptoPriceController {

    private final CryptoPriceServiceImpl cryptoPriceServiceImpl;

    @GetMapping("/latest-price")
    public CryptoPrice getLatestBestAggregatedPrice(@RequestParam String symbol) {
        return cryptoPriceServiceImpl.getLatestBestPrice(symbol);
    }
}
