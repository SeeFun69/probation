package com.rasyid.projectprobation.controller;

import com.rasyid.projectprobation.dto.*;
import com.rasyid.projectprobation.service.AuthenticationService;
import com.rasyid.projectprobation.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public static final String SUCCESS = "Success";

    @PostMapping("/register")
    public ResponseEntity<APIResponse> register(@RequestBody @Valid RegisterRequest request) {

        AuthenticationResponse response = authenticationService.register(request);
        APIResponse<AuthenticationResponse> responseDTO = APIResponse
                .<AuthenticationResponse>builder()
                .status(SUCCESS)
                .results(response)
                .build();

        log.info("register response {}", ValueMapper.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<APIResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        APIResponse<AuthenticationResponse> responseDTO = APIResponse
                .<AuthenticationResponse>builder()
                .status(SUCCESS)
                .results(response)
                .build();

        log.info("register response {}", ValueMapper.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
