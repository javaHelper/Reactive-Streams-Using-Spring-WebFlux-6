package com.example.reactivesubscriber;

import com.example.reactivesubscriber.client.ReactiveStocksSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ReactiveSubscriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveSubscriberApplication.class, args);
	}

	@Autowired
	private ReactiveStocksSubscriber reactiveStocksSubscriber;

	@PostConstruct
	public void subscribeToStockTradingApp() {
		reactiveStocksSubscriber.getOneStock("1").block();

		log.info("**************************************");
		reactiveStocksSubscriber.getAllStocks().blockLast();

		// We can also use subscribe();
	}
}