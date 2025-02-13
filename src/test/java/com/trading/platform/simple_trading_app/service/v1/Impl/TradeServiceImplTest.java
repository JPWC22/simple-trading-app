package com.trading.platform.simple_trading_app.service.v1.Impl;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import com.trading.platform.simple_trading_app.entity.TransactionHistory;
import com.trading.platform.simple_trading_app.entity.User;
import com.trading.platform.simple_trading_app.repository.UserRepository;
import com.trading.platform.simple_trading_app.service.v1.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradeServiceImplTest {

    @Mock
    private CryptoPriceService cryptoPriceService;
    @Mock
    private WalletService walletService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buyCryptoTest() {
        String username = "testUser";
        String cryptoSymbol = "BTC";
        BigDecimal tradeQty = BigDecimal.valueOf(1);

        User user = User.builder().username(username).build();
        CryptoPrice price = CryptoPrice.builder().askPrice(new BigDecimal("50000")).build();
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .user(user)
                .cryptoPair("BTC")
                .tradeType("BUY")
                .tradeAmount(tradeQty)
                .price(price.getAskPrice())
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(cryptoPriceService.getLatestBestPrice(cryptoSymbol)).thenReturn(price);
        when(transactionService.recordTransaction(any(User.class), anyString(), anyString(), any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(transactionHistory);

        TransactionHistory result = tradeService.buyCrypto(username, cryptoSymbol, tradeQty);

        assertNotNull(result);
        assertEquals("BUY", result.getTradeType());
        assertEquals(tradeQty, result.getTradeAmount());
        assertEquals(price.getAskPrice(), result.getPrice());
        verify(walletService, times(1)).updateBalance(eq(user), eq("USDT"), any(BigDecimal.class), eq(-1));
        verify(walletService, times(1)).updateBalance(eq(user), eq("BTC"), eq(tradeQty), eq(1));
    }

    @Test
    void sellCryptoTest() {
        String username = "testUser";
        String cryptoSymbol = "BTC";
        BigDecimal tradeQty = BigDecimal.valueOf(1);

        User user = User.builder().username(username).build();
        CryptoPrice price = CryptoPrice.builder().bidPrice(new BigDecimal("40000")).build();
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .user(user)
                .cryptoPair("BTC")
                .tradeType("SELL")
                .tradeAmount(tradeQty)
                .price(price.getBidPrice())
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(cryptoPriceService.getLatestBestPrice(cryptoSymbol)).thenReturn(price);
        when(transactionService.recordTransaction(any(User.class), anyString(), anyString(), any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(transactionHistory);

        TransactionHistory result = tradeService.sellCrypto(username, cryptoSymbol, tradeQty);

        assertNotNull(result);
        assertEquals("SELL", result.getTradeType());
        assertEquals(tradeQty, result.getTradeAmount());
        assertEquals(price.getBidPrice(), result.getPrice());
        verify(walletService, times(1)).updateBalance(eq(user), eq("USDT"), any(BigDecimal.class), eq(1));
        verify(walletService, times(1)).updateBalance(eq(user), eq("BTC"), eq(tradeQty), eq(-1));
    }
}