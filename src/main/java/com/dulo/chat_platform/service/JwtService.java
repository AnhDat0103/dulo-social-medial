package com.dulo.chat_platform.service;

import org.springframework.security.core.Authentication;

public interface JwtService {

    public String generateJwtToken(Authentication authentication);

}
