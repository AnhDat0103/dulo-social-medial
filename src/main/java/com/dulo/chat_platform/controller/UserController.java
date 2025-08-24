package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public String createUser(@RequestBody @Valid RegistrationRequest user) {
        if(user != null) {
            return "User created";
        } else
            return "User not found";
    }
}
