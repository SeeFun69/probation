package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import com.rasyid.projectprobation.entity.SaleOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MQOrderService {

    private final OrderService orderService;
    /**
     * Listen to the order message queue and consume
     *
     * @param saleOrder
     */
    @RabbitListener(queues = MyRabbitMQConfig.ORDER_QUEUE)
    public void createOrder(SaleOrder saleOrder) {
        log.info("Received the order message, the order user is: {}, the product name is: {}", saleOrder.getOrderUser(), saleOrder.getOrderName());
        /**
         * Call the database orderService to create order information
         */
        orderService.createOrder(saleOrder);
    }
}
