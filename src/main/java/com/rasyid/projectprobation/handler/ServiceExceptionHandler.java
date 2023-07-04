package com.rasyid.projectprobation.handler;

import com.rasyid.projectprobation.dto.APIResponse;
import com.rasyid.projectprobation.dto.ErrorDTO;
import com.rasyid.projectprobation.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler {

//    @Value("${twilio.MY_PHONE_NUMBER}")
//    private String myPhoneNumber;
//
//    private final SmsService smsService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        List<ErrorDTO> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
                    errors.add(errorDTO);
                });
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(errors);

//        if(serviceResponse.getStatus().equals("FAILED")){
//            var sms = SmsRequest.builder().phoneNumber(myPhoneNumber).message(serviceResponse.getErrors().toString()).build();
//            smsService.sendSms(sms);
//
//            log.info("SmsController {}", sms);
//        }
        return serviceResponse;
    }

    @ExceptionHandler(BusinessException.class)
    public APIResponse<?> handleServiceException(BusinessException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponse<?> handleGeneralException(Exception e){
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", e.getMessage())));
        return serviceResponse;
    }
}
