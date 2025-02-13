package com.trading.platform.simple_trading_app.controller.v1;

import com.trading.platform.simple_trading_app.entity.TransactionHistory;
import com.trading.platform.simple_trading_app.service.v1.TradeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/trade")
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/buy")
    public ResponseEntity<TransactionHistory> buyCrypto(@RequestBody TradeRequest tradeRequest) {
        return ResponseEntity.ok(tradeService.buyCrypto(tradeRequest.getUsername(), tradeRequest.getCryptoPair(), tradeRequest.getTradeQty()));
    }

    @PostMapping("/sell")
    public ResponseEntity<TransactionHistory> sellCrypto(@RequestBody TradeRequest tradeRequest) {
        return ResponseEntity.ok(tradeService.sellCrypto(tradeRequest.getUsername(), tradeRequest.getCryptoPair(), tradeRequest.getTradeQty()));
    }
}

@Getter
@Setter
class TradeRequest {
    private String username;
    private String cryptoPair;
    private BigDecimal tradeQty;
}