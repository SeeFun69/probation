package com.rasyid.projectprobation.service.impl;

import com.rasyid.projectprobation.base.mapper.StockMapper;
import com.rasyid.projectprobation.dto.StockDTO;
import com.rasyid.projectprobation.entity.Stock;
import com.rasyid.projectprobation.exception.BusinessException;
import com.rasyid.projectprobation.service.StockService;
import com.rasyid.projectprobation.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void decrByStock(String stockName) {
        stockMapper.updateStockByStockName(stockName);
    }

    @Override
    public Integer selectStockByName(String stockName) {
        return stockMapper.selectStockByName(stockName);
    }

    @Override
    public StockDTO createStock(StockDTO stockDto) throws BusinessException {
        StockDTO stockResponseDTO;
        try {
            log.info("StockService: createStock");
            Stock stock = ValueMapper.convertToEntity(stockDto);
            log.debug("Stock: createStock {}", ValueMapper.jsonAsString(stockDto));

            stockMapper.insertStock(stock);
            Long id = stock.getId();

            stockResponseDTO = stockMapper.findById(id);
            log.debug("Stock: createStock {}", ValueMapper.jsonAsString(stockDto));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting stock to database , Exception message {}", ex.getMessage());
            throw new BusinessException("Exception occurred while create a new stock");
        }
        return stockResponseDTO;
    }

    @Override
    public List<StockDTO> getAllStock() {
        List<StockDTO> stockDTOList = null;
        try {
            List<Stock> listStock = stockMapper.selectAll();
            if (!listStock.isEmpty()){
                stockDTOList = listStock.stream().map(ValueMapper::convertToDTO).collect(Collectors.toList());
            }
            stockDTOList = Collections.emptyList();
            log.debug("StockService:getStock retrieving stock from database  {}", ValueMapper.jsonAsString(stockDTOList));

        } catch (Exception ex) {
            log.error("Exception occurred while persisting stock to database , Exception message {}", ex.getMessage());
            throw new BusinessException("Exception occurred while fetch all stock from database");
        }

        return stockDTOList;
    }
}
