package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.AuthenticationRequest;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.UserStatus;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public String authentication(AuthenticationRequest request) {
        User user = userRepository.findByEmailAndStatus(request.getEmail(), UserStatus.ACTIVE);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }else{
            if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
                return "Authentication successful";
            }else{
                throw new IllegalArgumentException("Invalid password");
            }
        }
    }
}
