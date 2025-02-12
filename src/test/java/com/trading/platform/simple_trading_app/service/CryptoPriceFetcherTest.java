package com.trading.platform.simple_trading_app.service;

import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import com.trading.platform.simple_trading_app.repository.CryptoPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CryptoPriceFetcherTest {

    @Mock
    private PriceProvider priceProvider1;

    @Mock
    private PriceProvider priceProvider2;

    @Mock
    private CryptoPriceAggregator cryptoPriceAggregator;

    @Mock
    private CryptoPriceRepository cryptoPriceRepository;

    @InjectMocks
    private CryptoPriceFetcher cryptoPriceFetcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFetchAndStoreAggregatedPrices() {
        CryptoPrice btcPrice1 = CryptoPrice.builder().cryptoPair("BTCUSDT").bidPrice(40000.00).askPrice(40500.00).updatedAt(LocalDateTime.now()).build();
        CryptoPrice btcPrice2 = CryptoPrice.builder().cryptoPair("BTCUSDT").bidPrice(41000.00).askPrice(40300.00).updatedAt(LocalDateTime.now()).build();
        CryptoPrice ethPrice = CryptoPrice.builder().cryptoPair("ETHUSDT").bidPrice(2600.00).askPrice(2550.00).updatedAt(LocalDateTime.now()).build();

        List<CryptoPrice> fetchedPrices1 = List.of(btcPrice1);
        List<CryptoPrice> fetchedPrices2 = List.of(btcPrice2, ethPrice);

        List<CryptoPrice> aggregatedPrices = List.of(
                CryptoPrice.builder().cryptoPair("BTCUSDT").bidPrice(41000.00).askPrice(40300.00).updatedAt(LocalDateTime.now()).build(),
                CryptoPrice.builder().cryptoPair("ETHUSDT").bidPrice(2600.00).askPrice(2550.00).updatedAt(LocalDateTime.now()).build()
        );

        // Mock behavior for multiple providers
        when(priceProvider1.fetchPrices()).thenReturn(fetchedPrices1);
        when(priceProvider2.fetchPrices()).thenReturn(fetchedPrices2);

        // Inject providers into the fetcher
        cryptoPriceFetcher = new CryptoPriceFetcher(List.of(priceProvider1, priceProvider2), cryptoPriceAggregator, cryptoPriceRepository);

        when(cryptoPriceAggregator.aggregate(anyList())).thenReturn(aggregatedPrices);

        cryptoPriceFetcher.fetchAndStorePrices();

        ArgumentCaptor<List<CryptoPrice>> captor = ArgumentCaptor.forClass(List.class);
        verify(cryptoPriceRepository).saveAll(captor.capture());

        List<CryptoPrice> savedPrices = captor.getValue();
        assertEquals(2, savedPrices.size());

        assertEquals("BTCUSDT", savedPrices.get(0).getCryptoPair());
        assertEquals(41000.00, savedPrices.get(0).getBidPrice());
        assertEquals(40300.00, savedPrices.get(0).getAskPrice());

        assertEquals("ETHUSDT", savedPrices.get(1).getCryptoPair());
        assertEquals(2600.00, savedPrices.get(1).getBidPrice());
        assertEquals(2550.00, savedPrices.get(1).getAskPrice());

        verify(cryptoPriceRepository, times(1)).saveAll(any());
    }
}
