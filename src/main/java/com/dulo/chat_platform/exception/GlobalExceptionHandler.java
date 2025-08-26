package com.dulo.chat_platform.exception;

import com.dulo.chat_platform.dto.response.ErrorApiResponse;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorApiResponse<ErrorResponse>> badCredentialsHandle(BadCredentialsException e){
        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorEnum.BAD_CREDENTIALS.getCode())
                .message(ErrorEnum.BAD_CREDENTIALS.getMessage())
                .build();
        return ResponseEntity.badRequest().body(
                ErrorApiResponse.<ErrorResponse>builder()
                        .errors(response)
                        .build()
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorApiResponse<ErrorResponse>> disabledExceptionHandle() {
        ErrorResponse error = ErrorResponse.builder()
                .code(ErrorEnum.DISABLED_EXCEPTION.getCode())
                .message(ErrorEnum.DISABLED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(
                ErrorApiResponse.<ErrorResponse>builder()
                        .errors(error)
                        .build()
        );
    }
}
