package com.trading.platform.simple_trading_app.service.v1.Impl;

import com.trading.platform.simple_trading_app.entity.User;
import com.trading.platform.simple_trading_app.entity.Wallet;
import com.trading.platform.simple_trading_app.repository.UserRepository;
import com.trading.platform.simple_trading_app.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    private WalletServiceImpl walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        walletService = new WalletServiceImpl(walletRepository, userRepository);
    }

    @Test
    void updateBalance_addAmount_success() {
        User user = new User();
        Wallet wallet = Wallet.builder().user(user).asset("BTC").balance(BigDecimal.valueOf(100)).build();
        Set<Wallet> wallets = new HashSet<>();
        wallets.add(wallet);
        user.setWallets(wallets);
        walletService.updateBalance(user, "BTC", BigDecimal.valueOf(50), 1);

        ArgumentCaptor<Wallet> walletCaptor = ArgumentCaptor.forClass(Wallet.class);
        verify(walletRepository).save(walletCaptor.capture());
        assertEquals(BigDecimal.valueOf(150), walletCaptor.getValue().getBalance());
    }

    @Test
    void updateBalance_subtractAmount_throwsExceptionWhenInsufficientBalance() {
        User user = new User();
        Wallet wallet = Wallet.builder().user(user).asset("BTC").balance(BigDecimal.valueOf(30)).build();
        Set<Wallet> wallets = new HashSet<>();
        wallets.add(wallet);
        user.setWallets(wallets);
        Exception exception = assertThrows(RuntimeException.class, () ->
                walletService.updateBalance(user, "BTC", BigDecimal.valueOf(50), -1));

        assertEquals("Not enough balance for transaction", exception.getMessage());
    }
}