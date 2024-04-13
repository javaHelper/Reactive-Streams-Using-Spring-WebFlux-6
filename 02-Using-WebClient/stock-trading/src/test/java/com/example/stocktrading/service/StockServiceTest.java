package com.example.stocktrading.service;

import com.example.stockmarketapi.stockpublish.StockPublishResponse;
import com.example.stocktrading.client.StockMarketClient;
import com.example.stocktrading.dto.StockRequest;
import com.example.stocktrading.exception.StockCreationException;
import com.example.stocktrading.model.Stock;
import com.example.stocktrading.repository.StocksRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

public class StockServiceTest {
    public static final String STOCK_ID = "661a2396db35b8418c30c1cf";
    public static final String STOCK_NAME = "Globomantics";
    public static final BigDecimal STOCK_PRICE = BigDecimal.TEN;
    public static final String STOCK_CURRENCY = "USD";

    @InjectMocks
    private StocksService stocksService;

    @Mock
    private StocksRepository stocksRepository;

    @Mock
    private StockMarketClient stockMarketClient;
    
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateStock(){
        StockRequest stockRequest = StockRequest.builder()
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();

        Stock stock = Stock.builder().id(STOCK_ID).name(STOCK_NAME).price(STOCK_PRICE).currency(STOCK_CURRENCY).build();

        StockPublishResponse response = StockPublishResponse.builder()
                .stockName(STOCK_NAME)
                .price(STOCK_PRICE)
                .currencyName(STOCK_CURRENCY)
                .status("SUCCESS")
                .build();

        Mockito.when(stocksRepository.save(Mockito.any())).thenReturn(Mono.just(stock));
        Mockito.when(stockMarketClient.publishStock(Mockito.any())).thenReturn(Mono.just(response));


        StepVerifier.create(stocksService.createStock(stockRequest))
                .assertNext(stockResponse -> {
                    Assertions.assertNotNull(stockResponse);
                    Assertions.assertEquals(STOCK_ID, stockResponse.getId());
                    Assertions.assertEquals(STOCK_NAME, stockResponse.getName());
                    Assertions.assertEquals(STOCK_PRICE, stockResponse.getPrice());
                    Assertions.assertEquals(STOCK_CURRENCY, stockResponse.getCurrency());
                })
                .verifyComplete();
    }

    @Test
    void shouldThrowStockCreationExceptionWhenUnableToSave(){
        StockRequest stockRequest = StockRequest.builder()
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();
        Mockito.when(stocksRepository.save(Mockito.any())).thenThrow(new RuntimeException("Connection Lost"));

        StepVerifier.create(stocksService.createStock(stockRequest))
                .verifyError(StockCreationException.class);
    }

    @Test
    void shouldThrowStockCreationExceptionWhenStockMarketFailed(){
        StockRequest stockRequest = StockRequest.builder()
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();

        Stock stock = Stock.builder().id(STOCK_ID).name(STOCK_NAME).price(STOCK_PRICE).currency(STOCK_CURRENCY).build();

        StockPublishResponse response = StockPublishResponse.builder()
                .stockName(STOCK_NAME)
                .price(STOCK_PRICE)
                .currencyName(STOCK_CURRENCY)
                .status("FAIL")
                .build();

        Mockito.when(stocksRepository.save(Mockito.any())).thenReturn(Mono.just(stock));
        Mockito.when(stockMarketClient.publishStock(Mockito.any())).thenReturn(Mono.just(response));

        StepVerifier.create(stocksService.createStock(stockRequest))
                .verifyError(StockCreationException.class);
    }
}