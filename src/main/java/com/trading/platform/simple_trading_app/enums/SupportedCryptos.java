package com.trading.platform.simple_trading_app.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum SupportedCryptos {
    BTCUSDT, ETHUSDT;

    public static boolean isSupported(String symbol) {
        return Arrays.stream(SupportedCryptos.values())
                .map(Enum::name)
                .collect(Collectors.toSet())
                .contains(symbol);
    }
}
