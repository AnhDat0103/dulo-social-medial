package com.dulo.chat_platform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "user_phone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone", unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone number must be 10 to 11 digits")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
