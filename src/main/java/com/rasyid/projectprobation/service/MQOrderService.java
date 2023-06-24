package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import com.rasyid.projectprobation.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQOrderService {
    @Autowired
    private OrderService orderService;
    /**
     * Listen to the order message queue and consume
     *
     * @param order
     */
    @RabbitListener(queues = MyRabbitMQConfig.ORDER_QUEUE)
    public void createOrder(Order order) {
        log.info("Received the order message, the order user is: {}, the product name is: {}", order.getOrder_user(), order.getOrder_name());
        /**
         * Call the database orderService to create order information
         */
        orderService.createOrder(order);
    }
}
