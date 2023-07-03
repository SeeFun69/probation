package com.rasyid.projectprobation.service.impl;

import com.rasyid.projectprobation.base.mapper.OrderMapper;
import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import com.rasyid.projectprobation.dto.FlashSaleReq;
import com.rasyid.projectprobation.entity.Order;
import com.rasyid.projectprobation.service.OrderService;
import com.rasyid.projectprobation.service.RedisService;
import com.rasyid.projectprobation.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void createOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    @Override
    public String flashSale(FlashSaleReq request, String username) {
        log.info("The users participating in the flash sale are：{}，The product being limited-time sale is：{}", username, request.getStockname());
        String message = null;
        //Send a message to the inventory message queue, reducing the inventory data by one.
        Long decrByResult = redisService.decrBy(request.getStockname());
        if (decrByResult >= 0) {
            /**
             * This signifies that there is still available stock for this item, allowing you to proceed with your purchase.
             */
            log.info("Users: {}, Quickly sell this item: {}, There is stock, You can proceed to place your order", username, request.getStockname());
            //Send a message to the inventory queue to reduce the inventory count by one
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.STOCK_EXCHANGE, MyRabbitMQConfig.STOCK_ROUTING_KEY, request.getStockname());

            //Send a message to the order queue to create an order
            Order order = ValueMapper.convertToEntity(request, username);
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.ORDER_EXCHANGE, MyRabbitMQConfig.ORDER_ROUTING_KEY, order);
            message = "User " + username + " flash sale " + request.getStockname() + " Success";
        } else {
            /**
             * Notify the user that the product is out of stock and inform them of the unsuccessful flash sale attempt
             */
            log.info("User: {} There is no remaining inventory for the product during the flash sale, and the flash sale has ended", username);
            message = "User: "+ username + " There is no remaining inventory for the product, and the flash sale has ended";
        }
        return message;
    }
}
