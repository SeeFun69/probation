package com.rasyid.projectprobation.service.impl;

import com.rasyid.projectprobation.base.mapper.OrderMapper;
import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import com.rasyid.projectprobation.dto.FlashSaleReq;
import com.rasyid.projectprobation.dto.OrderDTO;
import com.rasyid.projectprobation.entity.SaleOrder;
import com.rasyid.projectprobation.exception.BusinessException;
import com.rasyid.projectprobation.service.OrderService;
import com.rasyid.projectprobation.service.RedisService;
import com.rasyid.projectprobation.service.StockService;
import com.rasyid.projectprobation.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final RedisService redisService;

    private final RabbitTemplate rabbitTemplate;

    private final StockService stockService;

    private final RedisConnectionFactory redisConnectionFactory;

    @Override
    public void createOrder(SaleOrder saleOrder) {
        orderMapper.insertOrder(saleOrder);
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
            SaleOrder saleOrder = ValueMapper.convertToEntity(request, username);
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.ORDER_EXCHANGE, MyRabbitMQConfig.ORDER_ROUTING_KEY, saleOrder);
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

    @Override
    public String saleReguler(FlashSaleReq request, String username) {
        log.info("The participants of the flash sale include...: {}，The items available for the flash sale are...: {}", username, request.getStockname());
        String message = null;
        //To find the inventory of the product
        Integer stockCount = stockService.selectStockByName(request.getStockname());
        log.info("User: {} Participate in flash sale，The product's current stock level is...: {}", username, stockCount);
        if (stockCount > 0) {
            /**
             * There is remaining stock, allowing you to proceed with the flash sale. Reduce the inventory by one and make a purchase
             */
            //1、Reduce the stock by one
            stockService.decrByStock(request.getStockname());
            //2、Place an order
            SaleOrder saleOrder = ValueMapper.convertToEntity(request, username);
           createOrder(saleOrder);
            log.info("User: {}.The result of participating in the flash sale is: successful", username);
            message = username + " The result of participating in the flash sale is: success";
        } else {
            log.info("User: {}.The result of participating in the flash sale is: the flash sale has ended", username);
            message = username + " The result of participating in the flash sale is: the flash sale has ended";
        }
        return message;
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        List<OrderDTO> orderDTOList = null;
        try (RedisConnection jedisConnection = redisConnectionFactory.getConnection()) {
//            List<SaleOrder> saleOrderList = orderMapper.selectAll();
            byte[] jsonData = jedisConnection.get("all_orders".getBytes());
            if (jsonData != null) {
                List<SaleOrder> listOrder = ValueMapper.parseOrdersFromJson(new String(jsonData));
                orderDTOList = convertToOrderDTOList(listOrder);
            } else {
                List<SaleOrder> listOrder = orderMapper.selectAll();
                orderDTOList = convertToOrderDTOList(listOrder);
                saveOrdersToRedis(listOrder);
            }
            log.debug("OrderService:getOrder retrieving order from database  {}", ValueMapper.jsonAsString(orderDTOList));
        } catch (Exception e) {
            log.error("Exception occurred while persisting stock to database , Exception message {}", e.getMessage());
            throw new BusinessException("Exception occurred while fetch all order from database");
        }
        return orderDTOList;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES) //
    public void updateDataInRedis() {
        List<SaleOrder> listOrder = orderMapper.selectAll();
        if(listOrder.isEmpty()) {
            log.info("Order is empty");
        } else {
            log.info("Stock: updateFromDatabases {}", listOrder);
            saveOrdersToRedis(listOrder);
        }
    }

    private List<OrderDTO> convertToOrderDTOList(List<SaleOrder> orderList) {
        if (!orderList.isEmpty()) {
            return orderList.stream()
                    .map(ValueMapper::convertToOrderDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private void saveOrdersToRedis(List<SaleOrder> orders) {
        try (RedisConnection jedisConnection = redisConnectionFactory.getConnection()) {
            byte[] jsonData = ValueMapper.jsonAsString(orders).getBytes();
            jedisConnection.setEx("all_orders".getBytes(), 300, jsonData);
        }
    }
}
