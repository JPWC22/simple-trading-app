CREATE TABLE crypto_prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crypto_pair VARCHAR(50) NOT NULL,
    bid_price DOUBLE NOT NULL,
    ask_price DOUBLE NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT idx_crypto_pair UNIQUE (crypto_pair)
);
