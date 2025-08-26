package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.AuthenticationRequest;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.VerificationToken;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.entity.enums.UserStatus;
import com.dulo.chat_platform.exception.AppException;
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

//    @Override
//    public String authentication(AuthenticationRequest request) {
//        User user = userRepository.findByEmailAndStatus(request.getEmail(), UserStatus.ACTIVE);
//        if (user == null) {
//            throw new AppException(ErrorEnum.USER_NOT_FOUND);
//        }else{
//            if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
//                return "Authentication successful";
//            }else{
//                throw new AppException(ErrorEnum.INVALID_PASSWORD);
//            }
//        }
//    }

    @Override
    public String verify(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw  new AppException(ErrorEnum.VERIFICATION_TOKEN_INVALID);
        }
        if(verificationToken.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new AppException(ErrorEnum.VERIFICATION_TOKEN_EXPIRED);
        }
        User user = verificationToken.getUser();
        if(user == null){
            throw new AppException(ErrorEnum.USER_NOT_FOUND);
        }
        if(user.getStatus() == UserStatus.ACTIVE){
            throw new AppException(ErrorEnum.EMAIL_CERTIFICATED);
        }
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return "Email verified successfully";
    }
}
