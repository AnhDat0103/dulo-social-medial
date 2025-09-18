package com.dulo.chat_platform.service;

import com.dulo.chat_platform.dto.request.PasswordResetRequest;
import com.dulo.chat_platform.dto.response.UserResponse;

public interface AuthenticationService {

//    String authentication(AuthenticationRequest request);

    String verify(String token);

    UserResponse resetPassword(PasswordResetRequest passwordResetRequest, String email);
}
