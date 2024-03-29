package com.rasyid.projectprobation.handler;

import com.rasyid.projectprobation.dto.APIResponse;
import com.rasyid.projectprobation.dto.ErrorDTO;
import com.rasyid.projectprobation.exception.BusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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

//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public APIResponse<?> handleAccessDeniedException(AccessDeniedException exception) {
//        APIResponse<?> serviceResponse = new APIResponse<>();
//        serviceResponse.setStatus("FORBIDDEN");
//        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", "You do not have permission to access this resource")));
//        return serviceResponse;
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public APIResponse<?> handleExpiredJwtException(ExpiredJwtException exception) {
//        APIResponse<?> serviceResponse = new APIResponse<>();
//        serviceResponse.setStatus("UNAUTHORIZED");
//        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", "Token has expired")));
//        return serviceResponse;
//    }

//    @ExceptionHandler(JwtException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public APIResponse<?> handleJwtException(JwtException exception) {
//        APIResponse<?> serviceResponse = new APIResponse<>();
//        serviceResponse.setStatus("UNAUTHORIZED");
//        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", "Invalid token")));
//        return serviceResponse;
//    }
}
