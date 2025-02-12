package com.trading.platform.simple_trading_app.repository;

import com.trading.platform.simple_trading_app.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
