package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.VerificationToken;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.repository.VerificationTokenRepository;
import com.dulo.chat_platform.service.EmailVerificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final JavaMailSender mailSender;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public void sendVerificationEmail(User user) throws MessagingException {
        // create a token
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .type("EMAIL_VERIFICATION")
                .user(user)
                .expiryTime(LocalDateTime.now().plusMinutes(5)) // token valid for 5 minutes
                .build();

        // save the token to the database'
        verificationTokenRepository.save(verificationToken);

        String verificationLink = "http://localhost:8080/auth/verify?token=" + token;

        // send email
        MimeMessage message = mailSender.createMimeMessage();
        String subject = "DULO Chat Platform - Email Verification";
        String body =
                """
                   <div style="font-family: Arial, sans-serif; line-height: 1.6;">
                    <h2 style="color: #4CAF50;">Welcome to DULO Chat Platform!</h2>
                    <p>Thank you for registering. Please click the link below to verify your email address:</p>
                    <a href="%s" style="display: inline-block; padding: 10px 20px; color: #fff; background-color: #4CAF50; text-decoration: none; border-radius: 5px;">Verify Email</a>
                    <p>This link will expire in 5 minutes.</p>
                    <p>If you did not sign up for this account, please ignore this email.</p>
                    <p>Best regards,<br/>The DULO Team</p>
                   </div>
                  """.formatted(verificationLink);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setFrom("datnt.dev.2402@gmail.com");

        mailSender.send(message);
    }
}
