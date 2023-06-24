package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQStockService {
    @Autowired
    private StockService stockService;
    /**
     * Listen to the inventory message queue and consume
     * @param stockName
     */
    @RabbitListener(queues = MyRabbitMQConfig.STORY_QUEUE)
    public void decrByStock(String stockName) {
        log.info("The message commodity information received by the stock message queue is: {}", stockName);
        /**
         * Call the database service to reduce the inventory of the corresponding product in the database by one
         */
        stockService.decrByStock(stockName);
    }
}
