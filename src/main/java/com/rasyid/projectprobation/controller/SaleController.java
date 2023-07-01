package com.rasyid.projectprobation.controller;

import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import com.rasyid.projectprobation.dto.APIResponse;
import com.rasyid.projectprobation.dto.FlashSaleReq;
import com.rasyid.projectprobation.entity.Order;
import com.rasyid.projectprobation.service.OrderService;
import com.rasyid.projectprobation.service.RedisService;
import com.rasyid.projectprobation.service.StockService;
import com.rasyid.projectprobation.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
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

    public static final String SUCCESS = "Success";

    /**
     * Implementing the flash sale feature using Redis and message queues.
     * @return
     */
    @PostMapping("/sale")
    @ResponseBody
    public ResponseEntity<?> sale(@RequestBody FlashSaleReq request) {

        log.info("The users participating in the flash sale are：{}，The product being limited-time sale is：{}", request.getUsername(), request.getStockname());
        String message = null;
        //Send a message to the inventory message queue, reducing the inventory data by one.
        Long decrByResult = redisService.decrBy(request.getStockname());
        if (decrByResult >= 0) {
            /**
             * This signifies that there is still available stock for this item, allowing you to proceed with your purchase.
             */
            log.info("Users: {}, Quickly sell this item: {}, There is stock, You can proceed to place your order", request.getUsername(), request.getStockname());
            //Send a message to the inventory queue to reduce the inventory count by one
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.STORY_EXCHANGE, MyRabbitMQConfig.STORY_ROUTING_KEY, request.getStockname());

            //Send a message to the order queue to create an order
            Order order = ValueMapper.convertToEntity(request);
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.ORDER_EXCHANGE, MyRabbitMQConfig.ORDER_ROUTING_KEY, order);
            message = "User " + request.getUsername() + " flash sale " + request.getStockname() + " Success";
        } else {
            /**
             * Notify the user that the product is out of stock and inform them of the unsuccessful flash sale attempt
             */
            log.info("User: {} There is no remaining inventory for the product during the flash sale, and the flash sale has ended", request.getUsername());
            message = "User: "+ request.getUsername() + " There is no remaining inventory for the product, and the flash sale has ended";
        }

        APIResponse<?> responseDTO = APIResponse
                .builder()
                .status(SUCCESS)
                .results(message)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Implementing a pure database operation for the flash sale functionality
     * @return
     */
    @PostMapping("/saleDataBase")
    @ResponseBody
    public String secDataBase(@RequestBody FlashSaleReq request) {
        log.info("The participants of the flash sale include...: {}，The items available for the flash sale are...: {}", request.getUsername(), request.getStockname());
        String message = null;
        //To find the inventory of the product
        Integer stockCount = stockService.selectStockByName(request.getStockname());
        log.info("User: {} Participate in flash sale，The product's current stock level is...: {}", request.getUsername(), stockCount);
        if (stockCount > 0) {
            /**
             * There is remaining stock, allowing you to proceed with the flash sale. Reduce the inventory by one and make a purchase
             */
            //1、Reduce the stock by one
            stockService.decrByStock(request.getStockname());
            //2、Place an order
//            Order order = new Order();
//            order.setOrderName(request.getStockname());
//            order.setOrderUser(request.getUsername());
            Order order = ValueMapper.convertToEntity(request);
            orderService.createOrder(order);
            log.info("User: {}.The result of participating in the flash sale is: successful", request.getUsername());
            message = request.getUsername() + " The result of participating in the flash sale is: success";
        } else {
            log.info("User: {}.The result of participating in the flash sale is: the flash sale has ended", request.getUsername());
            message = request.getUsername() + " The result of participating in the flash sale is: the flash sale has ended";
        }
        return message;
    }
}
