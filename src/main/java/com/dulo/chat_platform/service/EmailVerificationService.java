package com.dulo.chat_platform.service;

import com.dulo.chat_platform.entity.User;
import jakarta.mail.MessagingException;

public interface EmailVerificationService {
    void sendVerificationEmail(User user) throws MessagingException;
}
