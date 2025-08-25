package com.dulo.chat_platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="verification_token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    private String token;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    private String type; // "EMAIL_VERIFICATION" or "PASSWORD_RESET"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
