package com.trading.platform.simple_trading_app.service.v1;

import com.trading.platform.simple_trading_app.entity.User;

import java.math.BigDecimal;

public interface WalletService {
    void updateBalance(User user, String asset, BigDecimal amount, int modifier);
}

