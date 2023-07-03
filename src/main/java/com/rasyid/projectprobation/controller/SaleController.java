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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class SaleController {
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
    public ResponseEntity<APIResponse> sale(@RequestBody FlashSaleReq request, @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        String message = orderService.flashSale(request, username);

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
    public String saleDataBase(@RequestBody FlashSaleReq request, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
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
//            Order order = new Order();
//            order.setOrderName(request.getStockname());
//            order.setOrderUser(request.getUsername());
            Order order = ValueMapper.convertToEntity(request, username);
            orderService.createOrder(order);
            log.info("User: {}.The result of participating in the flash sale is: successful", username);
            message = username + " The result of participating in the flash sale is: success";
        } else {
            log.info("User: {}.The result of participating in the flash sale is: the flash sale has ended", username);
            message = username + " The result of participating in the flash sale is: the flash sale has ended";
        }
        return message;
    }
}
