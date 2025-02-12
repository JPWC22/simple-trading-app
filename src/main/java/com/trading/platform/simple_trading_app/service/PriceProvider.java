package com.trading.platform.simple_trading_app.service;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;

import java.util.List;

public interface PriceProvider {
    List<CryptoPrice> fetchPrices();
}
