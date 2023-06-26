package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.dto.StockRequestDTO;
import com.rasyid.projectprobation.dto.StockResponseDTO;

public interface StockService {

    void decrByStock(String stockName);

    Integer selectByExample(String stockName);

    StockResponseDTO createStock(StockRequestDTO stockRequestDto);
}
