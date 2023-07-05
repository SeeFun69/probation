package com.rasyid.projectprobation.controller;

import com.rasyid.projectprobation.dto.APIResponse;
import com.rasyid.projectprobation.dto.FlashSaleReq;
import com.rasyid.projectprobation.dto.OrderDTO;
import com.rasyid.projectprobation.dto.SmsRequest;
import com.rasyid.projectprobation.service.OrderService;
import com.rasyid.projectprobation.service.SmsService;
import com.rasyid.projectprobation.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
@RequiredArgsConstructor
public class SaleController {

    private final OrderService orderService;

    @Value("${twilio.MY_PHONE_NUMBER}")
    private String myPhoneNumber;

    private final SmsService smsService;

    public static final String SUCCESS = "Success";

    /**
     * Implementing the flash sale feature using Redis and message queues.
     */
    @PostMapping("/sale")
    public ResponseEntity<APIResponse> sale(@RequestBody FlashSaleReq request, @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        String message = orderService.flashSale(request, username);

        APIResponse<?> responseDTO = APIResponse
                .builder()
                .status(SUCCESS)
                .results(message)
                .build();


//        if(responseDTO.getStatus().equals(SUCCESS)){
//            var sms = SmsRequest.builder().phoneNumber(myPhoneNumber).message(message).build();
//            smsService.sendSms(sms);
//
//            log.info("SmsController {}", sms);
//        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Implementing a pure database operation for the flash sale functionality
     */
    @PostMapping("/sale/database")
    public ResponseEntity<APIResponse> saleDataBase(@RequestBody FlashSaleReq request, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String message = orderService.saleReguler(request, username);

        APIResponse<?> responseDTO = APIResponse
                .builder()
                .status(SUCCESS)
                .results(message)
                .build();

        log.info("OrderController::GetAllOrder response {}", ValueMapper.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/order/all")
    public ResponseEntity<APIResponse> getAllOrder(){
        List<OrderDTO> getOrder = orderService.getAllOrder();

        APIResponse<List<OrderDTO>> responseDTO = APIResponse
                .<List<OrderDTO>>builder()
                .status(SUCCESS)
                .results(getOrder)
                .build();

        log.info("OrderController::GetAllOrder response {}", ValueMapper.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
