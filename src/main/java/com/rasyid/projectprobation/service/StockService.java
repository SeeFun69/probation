package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.dto.StockDTO;

import java.util.List;

public interface StockService {

    void decrByStock(String stockName);

    Integer selectStockByName(String stockName);

    StockDTO createStock(StockDTO stockDto);

    List<StockDTO> getAllStock();
}
