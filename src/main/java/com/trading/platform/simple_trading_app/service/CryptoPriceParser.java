package com.trading.platform.simple_trading_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.platform.simple_trading_app.entity.CryptoPrice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CryptoPriceParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CryptoPrice> parse(String json, String symbolKey, String bidKey, String askKey, boolean nested) {
        try {
            JsonNode root = objectMapper.readTree(json);
            if (nested) {
                root = root.get("data");
            }

            List<CryptoPrice> prices = new ArrayList<>();
            for (JsonNode node : root) {
                String symbol = node.get(symbolKey).asText().toUpperCase();
                double bidPrice = node.get(bidKey).asDouble();
                double askPrice = node.get(askKey).asDouble();

                prices.add(CryptoPrice.builder()
                        .cryptoPair(symbol)
                        .bidPrice(bidPrice)
                        .askPrice(askPrice)
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
            return prices;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }
}


