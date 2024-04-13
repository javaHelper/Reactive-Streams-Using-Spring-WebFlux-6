package com.example.stockmarket.service;

import com.example.stockmarket.exception.StockPublishingException;
import com.example.stockmarketapi.stockpublish.StockPublishRequest;
import com.example.stockmarketapi.stockpublish.StockPublishResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StockPublishingService {

    public Mono<StockPublishResponse> publishStock(StockPublishRequest request) {
        return Mono.just(request)
            .map(this::persistStock);
    }

    private StockPublishResponse persistStock(StockPublishRequest request) {
        return StockPublishResponse.builder()
                .price(request.getPrice())
                .stockName(request.getStockName())
                .currencyName(request.getCurrencyName())
                .status(getStatus(request))
                .build();
    }

    private String getStatus(StockPublishRequest request) {
        if(request.getStockName().contains("-")) {
            throw new StockPublishingException("Stock name contains illegal character '-'");
        }
        return  "SUCCESS";
    }
}