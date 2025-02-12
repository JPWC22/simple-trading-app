package com.trading.platform.simple_trading_app.repository;

import com.trading.platform.simple_trading_app.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
}
