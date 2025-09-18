package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.AuthenticationRequest;
import com.dulo.chat_platform.dto.request.PasswordResetRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.service.AuthenticationService;
import com.dulo.chat_platform.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateJwtToken(authentication);
       return ApiResponse.<String>builder()
               .code("200")
               .message("Login successful")
               .data(token)
               .build();
    }

    @GetMapping("/verify-email")
    public String verify(@RequestParam String token){
        return authenticationService.verify(token);
    }

    @PatchMapping("/password-reset")
    public ApiResponse<UserResponse> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest, Authentication authentication){
        String email = authentication.getName();
        return ApiResponse.<UserResponse>builder()
                .code("200")
                .data(authenticationService.resetPassword(passwordResetRequest, email))
                .message("update password is successfully.")
                .build();
    }


}
