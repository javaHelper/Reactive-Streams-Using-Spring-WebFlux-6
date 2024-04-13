package com.example.stockmarket.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(StockPublishingException.class)
    public ProblemDetail handleStockPublishingException(StockPublishingException ex) {
        return ex.asProblemDetail();
    }
}