package com.rasyid.projectprobation.controller;

import com.rasyid.projectprobation.dto.APIResponse;
import com.rasyid.projectprobation.dto.StockRequestDTO;
import com.rasyid.projectprobation.dto.StockResponseDTO;
import com.rasyid.projectprobation.service.StockService;
import com.rasyid.projectprobation.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class StockController {

    private StockService stockService;
    public static final String SUCCESS = "Success";

    @PostMapping("/stock")
    public ResponseEntity<APIResponse> createNewStock(@RequestBody @Valid StockRequestDTO stockRequestDto) {

        log.info("StockController::createNewStock request body {}", ValueMapper.jsonAsString(stockRequestDto));

        StockResponseDTO stockResponseDTO = stockService.createStock(stockRequestDto);
        //Builder Design pattern

        APIResponse<StockResponseDTO> responseDTO = APIResponse
                .<StockResponseDTO>builder()
                .status(SUCCESS)
                .results(stockResponseDTO)
                .build();

        log.info("ProductController::createNewProduct response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
