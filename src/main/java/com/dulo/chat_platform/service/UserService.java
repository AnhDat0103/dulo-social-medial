package com.dulo.chat_platform.service;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import com.dulo.chat_platform.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(RegistrationRequest registrationRequest);

    UserResponse getUserByEmail(String email);

}
