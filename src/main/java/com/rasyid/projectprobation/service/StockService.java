package com.rasyid.projectprobation.service;

public interface StockService {

    void decrByStock(String stockName);

    Integer selectByExample(String stockName);
}
