package com.trading.platform.simple_trading_app.service.v1.Impl;

import com.trading.platform.simple_trading_app.entity.TransactionHistory;
import com.trading.platform.simple_trading_app.entity.User;
import com.trading.platform.simple_trading_app.repository.TransactionHistoryRepository;
import com.trading.platform.simple_trading_app.repository.UserRepository;
import com.trading.platform.simple_trading_app.service.v1.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final UserRepository userRepository;

    @Override
    public TransactionHistory recordTransaction(User user, String cryptoPair, String type, BigDecimal tradeAmount, BigDecimal price) {

        TransactionHistory transaction = TransactionHistory.builder()
                .user(user)
                .cryptoPair(cryptoPair)
                .tradeType(type)
                .tradeAmount(tradeAmount)
                .price(price)
                .tradeTime(LocalDateTime.now())
                .build();

        return transactionHistoryRepository.save(transaction);
    }
    @Override
    public List<TransactionHistory> getTransactionHistory(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User not found: " + username));

        return transactionHistoryRepository.findByUser(user);
    }
}
