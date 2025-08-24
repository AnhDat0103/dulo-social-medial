package com.dulo.chat_platform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    @NotNull( message = "Email cannot be null")
    private String email;

    @NotNull(message = "Full name cannot be null")
    private String fullName;

    @NotNull(message = "Date of birth cannot be null")
    private Date dob;

    private String avatar;

    private String phone;
}
