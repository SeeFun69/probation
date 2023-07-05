package com.rasyid.projectprobation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasyid.projectprobation.dto.FlashSaleReq;
import com.rasyid.projectprobation.dto.OrderDTO;
import com.rasyid.projectprobation.dto.StockDTO;
import com.rasyid.projectprobation.entity.SaleOrder;
import com.rasyid.projectprobation.entity.Stock;
import io.jsonwebtoken.io.IOException;

import java.util.List;

public class ValueMapper {

    public static Stock convertToEntity(StockDTO stockRequest){
        Stock stock = new Stock();
        stock.setName(stockRequest.getName());
        stock.setStock(stockRequest.getStock());
        return stock;
    }

    public static SaleOrder convertToEntity(FlashSaleReq req, String username){
        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setOrderUser(username);
        saleOrder.setOrderName(req.getStockname());
        return saleOrder;
    }

    public static StockDTO convertToStockDTO(Stock stock){
        StockDTO response = new StockDTO();
        response.setName(stock.getName());
        response.setStock(stock.getStock());
        return response;
    }

    public static OrderDTO convertToOrderDTO(SaleOrder saleOrder){
        OrderDTO response = new OrderDTO();
        response.setOrderName(saleOrder.getOrderName());
        response.setOrderUser(saleOrder.getOrderUser());
        return response;
    }

    public static List<SaleOrder> parseOrdersFromJson(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonData, new TypeReference<List<SaleOrder>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
