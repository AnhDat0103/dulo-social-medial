package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.AuthenticationRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
       return ApiResponse.<String>builder()
               .code("200")
               .message("Login successful")
               .data("login is successfully.")
               .build();
    }

    @GetMapping("/verify")
    public String verify(@RequestParam String token){
        return authenticationService.verify(token);
    }


}
