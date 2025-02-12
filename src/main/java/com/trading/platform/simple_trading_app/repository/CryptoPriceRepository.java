package com.trading.platform.simple_trading_app.repository;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {
    Optional<CryptoPrice> findTopByCryptoPairOrderByUpdatedAtDesc(String cryptoPair);
}
