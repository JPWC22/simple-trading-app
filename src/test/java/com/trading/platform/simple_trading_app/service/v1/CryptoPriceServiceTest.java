package com.trading.platform.simple_trading_app.service.v1;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import com.trading.platform.simple_trading_app.repository.CryptoPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CryptoPriceServiceTest {

    @Mock
    private CryptoPriceRepository cryptoPriceRepository;

    @InjectMocks
    private CryptoPriceService cryptoPriceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnLatestBestPrice_WhenSymbolExists() {
        CryptoPrice expectedPrice = CryptoPrice.builder()
                .cryptoPair("BTCUSDT")
                .bidPrice(41000.00)
                .askPrice(40300.00)
                .updatedAt(LocalDateTime.now())
                .build();

        when(cryptoPriceRepository.findTopByCryptoPairOrderByUpdatedAtDesc("BTCUSDT"))
                .thenReturn(Optional.of(expectedPrice));

        CryptoPrice result = cryptoPriceService.getLatestBestPrice("BTCUSDT");

        assertEquals(expectedPrice, result);
    }

    @Test
    void shouldThrowException_WhenSymbolNotFound() {
        when(cryptoPriceRepository.findTopByCryptoPairOrderByUpdatedAtDesc("ETHUSDT"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                cryptoPriceService.getLatestBestPrice("ETHUSDT"));

        assertEquals("Price data not found for symbol: ETHUSDT", exception.getMessage());
    }
}
