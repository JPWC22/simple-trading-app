# Simple Trading App

A demo trading platform for Bitcoin and Ethereum. This app provides basic functionality for trading, checking cryptocurrency prices, viewing wallet balances, and retrieving transaction history.

## Features

- **Trade Controller**: Buy and sell cryptocurrency
- **Crypto Price Controller**: Fetch the latest cryptocurrency prices
- **Wallet Controller**: View wallet balances
- **Trading History Controller**: Retrieve transaction history

## API Endpoints

### Trade

- **POST /v1/trade/sell**: Sell cryptocurrency
- **POST /v1/trade/buy**: Buy cryptocurrency

### Crypto Prices

- **GET /v1/crypto/latest-price**: Fetch the latest price of Bitcoin and Ethereum

### Wallet

- **GET /v1/api/wallets/balances/{username}**: View wallet balances for the given username

### Trading History

- **GET /v1/api/transaction-history/{username}**: Retrieve transaction history for the given username

## Running the Application

1. **Clone the repository:**
    ```bash
    git clone https://github.com/JPWC22/simple-trading-app.git
    cd simple-trading-app
    ```

2. **Ensure you have Java 17 installed.**

3. **Run the application:**
    ```bash
    ./gradlew bootRun
    ```

4. **Access Swagger UI at:**
    ```bash
    http://localhost:8080/swagger-ui/index.html
    ```
5. **Information to use with H2 database:**
   ```bash
      username: "john"
      crypto symbols: "BTCUSDT" or "ETHUSDT"
   ```
## Tech Stack

- **Spring Boot 3.4.2**
- **Java 17**
- **Spring Data JPA**
- **H2 Database (for testing)**
- **Springdoc OpenAPI (Swagger UI)**

## Testing

To run the tests, use the following command:

```bash
./gradlew test
```

---