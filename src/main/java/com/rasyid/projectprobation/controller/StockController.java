package com.rasyid.projectprobation.controller;

import com.rasyid.projectprobation.config.MyRabbitMQConfig;
import com.rasyid.projectprobation.dto.APIResponse;
import com.rasyid.projectprobation.dto.StockDTO;
import com.rasyid.projectprobation.entity.Stock;
import com.rasyid.projectprobation.service.RedisService;
import com.rasyid.projectprobation.service.StockService;
import com.rasyid.projectprobation.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String SUCCESS = "Success";

    @PostMapping("/stock")
    public ResponseEntity<APIResponse> createNewStock(@RequestBody @Valid StockDTO stockDto) {

        log.info("StockController::createNewStock request body {}", ValueMapper.jsonAsString(stockDto));

        StockDTO stockResponseDTO = stockService.createStock(stockDto);

        APIResponse<StockDTO> responseDTO = APIResponse
                .<StockDTO>builder()
                .status(SUCCESS)
                .results(stockResponseDTO)
                .build();

        log.info("StockController::createNewStock response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/stock/all")
    public ResponseEntity<APIResponse> getAllStock(){
        List<StockDTO> getStocks = stockService.getAllStock();

        APIResponse<List<StockDTO>> responseDTO = APIResponse
                .<List<StockDTO>>builder()
                .status(SUCCESS)
                .results(getStocks)
                .build();

        log.info("StockController::createNewStock response {}", ValueMapper.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/flash/sale")
    public ResponseEntity<?> flashSale(@RequestBody StockDTO stockDTO){
        redisService.put(stockDTO.getName(), stockDTO.getStock(), 10);
        APIResponse<?> responseDTO = APIResponse
                .builder()
                .status(SUCCESS)
                .results("Flash Sale Begin")
                .build();
        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }
}
