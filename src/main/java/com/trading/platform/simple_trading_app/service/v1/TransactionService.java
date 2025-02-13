package com.trading.platform.simple_trading_app.service.v1;

import com.trading.platform.simple_trading_app.entity.TransactionHistory;
import com.trading.platform.simple_trading_app.entity.User;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionHistory recordTransaction(User user, String cryptoPair, String type, BigDecimal tradeAmount, BigDecimal price);
}
