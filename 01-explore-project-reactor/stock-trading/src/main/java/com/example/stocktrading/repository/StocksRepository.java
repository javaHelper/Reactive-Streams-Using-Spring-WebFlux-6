package com.example.stocktrading.repository;

import com.example.stocktrading.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends ReactiveMongoRepository<Stock, String> {
}