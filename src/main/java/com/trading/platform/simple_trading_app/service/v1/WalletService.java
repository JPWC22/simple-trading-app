package com.trading.platform.simple_trading_app.service.v1;

import com.trading.platform.simple_trading_app.entity.User;
import com.trading.platform.simple_trading_app.entity.Wallet;

import java.math.BigDecimal;
import java.util.Set;

public interface WalletService {
    void updateBalance(User user, String asset, BigDecimal amount, int modifier);
    Set<Wallet> retrieveBalances(String username);
}

