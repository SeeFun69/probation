package com.rasyid.projectprobation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasyid.projectprobation.dto.StockRequestDTO;
import com.rasyid.projectprobation.dto.StockResponseDTO;
import com.rasyid.projectprobation.entity.Stock;

public class ValueMapper {

    public static Stock convertToEntity(StockRequestDTO stockRequest){
        Stock stock = new Stock();
        stock.setName(stockRequest.getName());
        stock.setStock(stockRequest.getStock());
        return stock;
    }

    public static StockResponseDTO convertToDTO(Stock stock){
        StockResponseDTO response = new StockResponseDTO();
        response.setName(stock.getName());
        response.setStock(stock.getStock());
        return response;
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
