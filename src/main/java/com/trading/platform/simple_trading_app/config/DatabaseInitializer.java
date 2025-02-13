package com.trading.platform.simple_trading_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    @Transactional
    public void initiateDatabase() {
        jdbcTemplate.update("INSERT INTO users (username) VALUES (?)", "john");
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users WHERE username = 'john'", Long.class);

        insertWallet(userId, "USDT", new BigDecimal("50000"));
        insertWallet(userId, "ETHUSDT", BigDecimal.ZERO);
        insertWallet(userId, "BTCUSDT", BigDecimal.ZERO);
    }

    private void insertWallet(Long userId, String asset, BigDecimal balance) {
        jdbcTemplate.update("INSERT INTO wallets (user_id, asset, balance) VALUES (?, ?, ?)", userId, asset, balance);
    }
}