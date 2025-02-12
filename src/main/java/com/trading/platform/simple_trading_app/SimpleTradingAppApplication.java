package com.trading.platform.simple_trading_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SimpleTradingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleTradingAppApplication.class, args);
	}

}
