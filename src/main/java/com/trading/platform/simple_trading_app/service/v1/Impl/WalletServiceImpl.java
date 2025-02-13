package com.trading.platform.simple_trading_app.service.v1.Impl;

import com.trading.platform.simple_trading_app.entity.User;
import com.trading.platform.simple_trading_app.entity.Wallet;
import com.trading.platform.simple_trading_app.repository.WalletRepository;
import com.trading.platform.simple_trading_app.service.v1.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public void updateBalance(User user, String asset, BigDecimal amount, int modifier) {
        Optional<Wallet> walletOptional = user.getWallets().stream()
                .filter(w -> w.getAsset().equalsIgnoreCase(asset))
                .findFirst();

        Wallet wallet = walletOptional.orElseGet(() ->
                Wallet.builder()
                        .user(user)
                        .asset(asset)
                        .balance(BigDecimal.ZERO)
                        .build());

        BigDecimal newBalance = modifier > 0 ? wallet.getBalance().add(amount)
                : wallet.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Not enough balance for transaction");
        }
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }
}