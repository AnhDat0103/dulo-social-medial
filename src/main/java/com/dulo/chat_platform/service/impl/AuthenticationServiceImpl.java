package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.PasswordResetRequest;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.VerificationToken;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.entity.enums.UserStatus;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.mapper.UserMapper;
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
    private final UserMapper userMapper;

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

    @Override
    public UserResponse resetPassword(PasswordResetRequest passwordResetRequest, String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        if(!passwordEncoder.matches(passwordResetRequest.getOldPassword(), user.getPassword())){
            throw new AppException(ErrorEnum.PASSWORD_NOT_MATCH);
        }
        if(!passwordResetRequest.getNewPassword().equals(passwordResetRequest.getConfirmPassword())){
            throw new AppException(ErrorEnum.PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
