package com.example.reactivesubscriber.client;

import com.example.reactivesubscriber.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class ReactiveStocksSubscriber {
    private static final String BASE_URL = "http://localhost:8080/";
    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public Mono<Stock> getOneStock(String id){
        return webClient().get()
                .uri("/stocks/{id}", id)
                .retrieve()
                .bodyToMono(Stock.class)
                .doOnNext(stock -> log.info("On next stock :{}", stock))
                .doFinally(x -> log.info("On Complete"));
    }
    
    public Flux<Stock> getAllStocks(){
        return webClient().get()
                .uri("/stocks")
                .retrieve()
                .bodyToFlux(Stock.class)
                .doOnNext(stock -> log.info("On Next Stock :{}", stock));
    }
}