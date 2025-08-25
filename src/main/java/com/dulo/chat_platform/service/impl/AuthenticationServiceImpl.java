package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.AuthenticationRequest;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.VerificationToken;
import com.dulo.chat_platform.entity.enums.UserStatus;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.repository.VerificationTokenRepository;
import com.dulo.chat_platform.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

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

    @Override
    public String verify(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        if(verificationToken.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Token expired");
        }
        User user = verificationToken.getUser();
        if(user == null){
            throw new IllegalArgumentException("User not found");
        }
        if(user.getStatus() == UserStatus.ACTIVE){
            throw new IllegalArgumentException("User already verified");
        }
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return "Email verified successfully";
    }
}
