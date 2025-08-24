package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid RegistrationRequest user) {
        UserResponse userResponse = userService.createUser(user);
        return ApiResponse.<UserResponse>builder()
                .code("200")
                .message("User created successfully")
                .data(userResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<UserResponse> getUserByEmail(@RequestParam("email") String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        return ApiResponse.<UserResponse>builder()
                .code("200")
                .message("User retrieved successfully")
                .data(userResponse)
                .build();
    }
}
