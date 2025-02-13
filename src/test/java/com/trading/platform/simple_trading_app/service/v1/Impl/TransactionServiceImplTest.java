package com.trading.platform.simple_trading_app.service.v1.Impl;

import com.trading.platform.simple_trading_app.entity.User;
import com.trading.platform.simple_trading_app.entity.TransactionHistory;
import com.trading.platform.simple_trading_app.repository.TransactionHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServiceImpl(transactionHistoryRepository);
    }

    @Test
    void recordTransaction() {
        User user = new User();
        TransactionHistory transaction = transactionService.recordTransaction(user, "BTC_USD", "BUY", BigDecimal.valueOf(10), BigDecimal.valueOf(20000));

        ArgumentCaptor<TransactionHistory> captor = ArgumentCaptor.forClass(TransactionHistory.class);
        verify(transactionHistoryRepository).save(captor.capture());
        assertEquals("BTC_USD", captor.getValue().getCryptoPair());
        assertEquals("BUY", captor.getValue().getTradeType());
    }
}