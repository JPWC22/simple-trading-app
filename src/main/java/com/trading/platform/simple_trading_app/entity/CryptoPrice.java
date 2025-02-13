package com.trading.platform.simple_trading_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_prices", indexes = {
        @Index(name = "idx_crypto_pair", columnList = "cryptoPair")
})
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CryptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cryptoPair;

    @Column(nullable = false)
    private BigDecimal bidPrice;

    @Column(nullable = false)
    private BigDecimal askPrice;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}


