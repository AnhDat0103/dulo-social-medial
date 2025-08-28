package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import com.dulo.chat_platform.dto.request.UserPatchRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("sign-up")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid RegistrationRequest user) {
        UserResponse userResponse = userService.createUser(user);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.name())
                .message("User created successfully")
                .data(userResponse)
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo(Authentication authentication) {
        String email = authentication.getName();
        UserResponse userResponse = userService.getUserByEmail(email);
        return ApiResponse.<UserResponse>builder()
                .code("200")
                .message("User retrieved successfully")
                .data(userResponse)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getOtherUserInfo(@PathVariable int id){
        UserResponse userResponse = userService.getUserById(id);
        return ApiResponse.<UserResponse>builder()
                .code("200")
                .message("User retrieved successfully")
                .data(userResponse)
                .build();
    }

    @PatchMapping
    public ApiResponse<UserResponse> updateInfo(@RequestBody UserPatchRequest userPatchRequest, Authentication authentication){
        String email = authentication.getName();
        UserResponse updatedUser = userService.updateUserByPatch(email, userPatchRequest);
        return ApiResponse.<UserResponse>builder()
                .code("200")
                .message("User is updated")
                .data(updatedUser)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUser(@PathVariable int id){
        userService.deleteUserById(id);
        return ApiResponse.builder()
                .code("200")
                .message("User is deleted")
                .build();
    }
}
