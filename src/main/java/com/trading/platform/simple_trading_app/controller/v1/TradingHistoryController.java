package com.trading.platform.simple_trading_app.controller.v1;

import com.trading.platform.simple_trading_app.entity.TransactionHistory;
import com.trading.platform.simple_trading_app.service.v1.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/transaction-history")
public class TradingHistoryController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{username}")
    public ResponseEntity<List<TransactionHistory>> getTransactionHistory(@PathVariable String username) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(username));
    }
}