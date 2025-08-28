package com.dulo.chat_platform.exception;

import com.dulo.chat_platform.dto.response.ErrorApiResponse;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorEnum.INVALID_TOKEN.getCode())
                .message(ErrorEnum.INVALID_TOKEN.getMessage())
                .build();

        ErrorApiResponse<ErrorResponse> errorApiResponse = ErrorApiResponse.<ErrorResponse>builder()
                .errors(errorResponse)
                .build();


        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorApiResponse));
    }
}
