package com.trading.platform.simple_trading_app.service.v1;

import com.trading.platform.simple_trading_app.entity.TransactionHistory;

import java.math.BigDecimal;

public interface TradeService {
    TransactionHistory buyCrypto(String username, String cryptoPair, BigDecimal tradeAmount);
    TransactionHistory sellCrypto(String username, String cryptoPair, BigDecimal tradeAmount);
}
