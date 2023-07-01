package com.rasyid.projectprobation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasyid.projectprobation.dto.FlashSaleReq;
import com.rasyid.projectprobation.dto.StockDTO;
import com.rasyid.projectprobation.entity.Order;
import com.rasyid.projectprobation.entity.Stock;

public class ValueMapper {

    public static Stock convertToEntity(StockDTO stockRequest){
        Stock stock = new Stock();
        stock.setName(stockRequest.getName());
        stock.setStock(stockRequest.getStock());
        return stock;
    }

    public static Order convertToEntity(FlashSaleReq req){
        Order order = new Order();
        order.setOrderUser(req.getUsername());
        order.setOrderName(req.getStockname());
        return order;
    }

    public static StockDTO convertToDTO(Stock stock){
        StockDTO response = new StockDTO();
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
