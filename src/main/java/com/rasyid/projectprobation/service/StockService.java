package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.dto.StockDTO;

public interface StockService {

    void decrByStock(String stockName);

    Integer selectByExample(String stockName);

    StockDTO createStock(StockDTO stockDto);
}
