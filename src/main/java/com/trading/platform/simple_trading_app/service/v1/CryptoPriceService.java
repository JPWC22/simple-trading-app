package com.trading.platform.simple_trading_app.service.v1;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;

public interface CryptoPriceService {
    CryptoPrice getLatestBestPrice(String symbol);
}
