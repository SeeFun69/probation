package com.rasyid.projectprobation.controller;

import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import com.rasyid.projectprobation.entity.Order;
import com.rasyid.projectprobation.service.OrderService;
import com.rasyid.projectprobation.service.RedisService;
import com.rasyid.projectprobation.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SaleController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StockService stockService;
    /**
     * Implementing the flash sale feature using Redis and message queues.
     *
     * @param username
     * @param stockName
     * @return
     */
    @PostMapping( value = "/sale",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String sec(@RequestParam(value = "username") String username, @RequestParam(value = "stockName") String stockName) {

        log.info("The users participating in the flash sale are：{}，The product being limited-time sale is：{}", username, stockName);
        String message = null;
        //Send a message to the inventory message queue, reducing the inventory data by one.
        Long decrByResult = redisService.decrBy(stockName);
        if (decrByResult >= 0) {
            /**
             * This signifies that there is still available stock for this item, allowing you to proceed with your purchase.
             */
            log.info("Users: {}, Quickly sell this item: {}, There is stock, You can proceed to place your order", username, stockName);
            //Send a message to the inventory queue to reduce the inventory count by one
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.STORY_EXCHANGE, MyRabbitMQConfig.STORY_ROUTING_KEY, stockName);

            //Send a message to the order queue to create an order
            Order order = new Order();
            order.setOrder_name(stockName);
            order.setOrder_user(username);
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.ORDER_EXCHANGE, MyRabbitMQConfig.ORDER_ROUTING_KEY, order);
            message = "User" + username + "flash sale" + stockName + "Success";
        } else {
            /**
             * Notify the user that the product is out of stock and inform them of the unsuccessful flash sale attempt
             */
            log.info("User: {} There is no remaining inventory for the product during the flash sale, and the flash sale has ended", username);
            message = "User: "+ username + "There is no remaining inventory for the product, and the flash sale has ended";
        }
        return message;
    }
}
