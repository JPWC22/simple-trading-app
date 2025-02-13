package com.trading.platform.simple_trading_app.service.v1.Impl;

import com.trading.platform.simple_trading_app.entity.TransactionHistory;
import com.trading.platform.simple_trading_app.entity.User;
import com.trading.platform.simple_trading_app.repository.UserRepository;
import com.trading.platform.simple_trading_app.service.v1.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class TradeServiceImpl implements TradeService {

    public static final String USDT_CURRENCY = "USDT";

    private final CryptoPriceService cryptoPriceService;
    private final WalletService walletService;
    private final TransactionService transactionService;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TransactionHistory buyCrypto(String username, String cryptoSymbol, BigDecimal tradeQty) {
        return performTransaction(username, cryptoSymbol, tradeQty, true);
    }

    @Transactional
    @Override
    public TransactionHistory sellCrypto(String username, String cryptoSymbol, BigDecimal tradeQty) {
        return performTransaction(username, cryptoSymbol, tradeQty, false);
    }

    private TransactionHistory performTransaction(String username, String cryptoSymbol, BigDecimal quantity, boolean isBuying) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User not found: " + username));
        BigDecimal pricePerCoin = isBuying ? cryptoPriceService.getLatestBestPrice(cryptoSymbol).getAskPrice()
                : cryptoPriceService.getLatestBestPrice(cryptoSymbol).getBidPrice();
        BigDecimal totalFunds = pricePerCoin.multiply(quantity);

        walletService.updateBalance(user, USDT_CURRENCY, totalFunds, isBuying ? -1 : 1);
        walletService.updateBalance(user, cryptoSymbol, quantity, isBuying ? 1 : -1);

        return transactionService.recordTransaction(user, cryptoSymbol, isBuying ? "BUY" : "SELL", quantity, pricePerCoin);
    }
}