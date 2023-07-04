package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import com.rasyid.projectprobation.dto.StockDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MQStockService {

    private final StockService stockService;

    /**
     * Listen to the inventory message queue and consume
     */
    @RabbitListener(queues = MyRabbitMQConfig.STOCK_QUEUE)
    public void decrByStock(String stockName) {
        log.info("The message commodity information received by the stock message queue is: {}", stockName);
        /**
         * Call the database service to reduce the inventory of the corresponding product in the database by one
         */
        stockService.decrByStock(stockName);
    }
//    @RabbitListener(queues = MyRabbitMQConfig.STOCK_QUEUE)
//    public void createStock(StockDTO stockDto) {
//        log.info("The message commodity information received by the stock message queue is: {}", stockDto);
//        /**
//         * Call the database service to reduce the inventory of the corresponding product in the database by one
//         */
//        stockService.createStock(stockDto);
//    }
}
