package com.trading.platform.simple_trading_app.repository;

import com.trading.platform.simple_trading_app.entity.User;
import com.trading.platform.simple_trading_app.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserAndAsset(User user, String asset);
    Set<Wallet> findByUser(User user);
}
