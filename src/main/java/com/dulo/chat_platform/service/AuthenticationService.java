package com.dulo.chat_platform.service;

import com.dulo.chat_platform.dto.request.AuthenticationRequest;

public interface AuthenticationService {

    String authentication(AuthenticationRequest request);

    String verify(String token);
}
