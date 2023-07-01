package com.rasyid.projectprobation.controller;

import com.rasyid.projectprobation.dto.APIResponse;
import com.rasyid.projectprobation.dto.StockDTO;
import com.rasyid.projectprobation.service.RedisService;
import com.rasyid.projectprobation.service.StockService;
import com.rasyid.projectprobation.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private RedisService redisService;

    public static final String SUCCESS = "Success";

    @PostMapping("/stock")
    public ResponseEntity<APIResponse> createNewStock(@RequestBody @Valid StockDTO stockDto) {

        log.info("StockController::createNewStock request body {}", ValueMapper.jsonAsString(stockDto));

        StockDTO stockResponseDTO = stockService.createStock(stockDto);
        //Builder Design pattern

        APIResponse<StockDTO> responseDTO = APIResponse
                .<StockDTO>builder()
                .status(SUCCESS)
                .results(stockResponseDTO)
                .build();

        log.info("ProductController::createNewProduct response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
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
