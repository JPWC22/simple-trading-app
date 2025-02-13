package com.trading.platform.simple_trading_app.controller.v1;

import com.trading.platform.simple_trading_app.entity.Wallet;
import com.trading.platform.simple_trading_app.service.v1.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/v1/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/balances/{username}")
    public ResponseEntity<Set<Wallet>> retrieveBalance(@PathVariable String username) {
        return ResponseEntity.ok(walletService.retrieveBalances(username));
    }
}