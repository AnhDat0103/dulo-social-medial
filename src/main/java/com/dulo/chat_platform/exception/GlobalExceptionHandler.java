package com.dulo.chat_platform.exception;

import com.dulo.chat_platform.dto.response.ErrorApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorApiResponse<ErrorResponse>> exceptionHandler(AppException e){
        ErrorResponse response = ErrorResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(
                ErrorApiResponse.<ErrorResponse>builder()
                        .errors(response)
                        .build());
    }
}
