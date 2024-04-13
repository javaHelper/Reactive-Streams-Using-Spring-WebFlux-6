package com.example.stocktrading.it;

import com.example.stockmarketapi.currencyrate.CurrencyRate;
import com.example.stocktrading.client.StockMarketClient;
import com.example.stocktrading.dto.StockResponse;
import com.example.stocktrading.model.Stock;
import com.example.stocktrading.repository.StocksRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StockIT {
    public static final String STOCK_ID = "661a2396db35b8418c30c1cf";
    public static final String STOCK_NAME = "Globomantics";
    public static final BigDecimal STOCK_PRICE = BigDecimal.TEN;
    public static final String STOCK_CURRENCY = "USD";

    @MockBean
    private StocksRepository stocksRepository;

    @MockBean
    private StockMarketClient stockMarketClient;

    @Autowired
    private WebTestClient client;

    @Test
    void shouldGetOneStock() {
        Stock stock = Stock.builder().id(STOCK_ID).name(STOCK_NAME).price(STOCK_PRICE).currency(STOCK_CURRENCY).build();

        CurrencyRate currencyRate = CurrencyRate.builder()
                .currencyName("USD")
                .rate(BigDecimal.ONE)
                .build();
        Mockito.when(stocksRepository.findById(STOCK_ID)).thenReturn(Mono.just(stock));
        Mockito.when(stockMarketClient.getCurrencyRates()).thenReturn(Flux.just(currencyRate));

        StockResponse stockResponse = client.get()
                .uri(uriBuilder -> uriBuilder.path("/stocks/{id}").build(STOCK_ID))
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockResponse.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(stockResponse);
        Assertions.assertEquals(stockResponse.getId(), STOCK_ID);
        Assertions.assertEquals(stockResponse.getName(), STOCK_NAME);
        Assertions.assertEquals(stockResponse.getPrice(), STOCK_PRICE);
        Assertions.assertEquals(stockResponse.getCurrency(), STOCK_CURRENCY);
    }

    @Test
    void shouldReturnNotFoundWhenGetOneStock() {
        Mockito.when(stocksRepository.findById(STOCK_ID)).thenReturn(Mono.empty());

        ProblemDetail problemDetail = client.get()
                .uri(uriBuilder -> uriBuilder.path("/stocks/{id}").build(STOCK_ID))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertTrue(problemDetail.getDetail().contains("Stock not found"));
    }
}