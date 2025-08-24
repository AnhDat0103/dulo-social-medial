package com.dulo.chat_platform.dto.request;

import com.dulo.chat_platform.validation.MatchPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@MatchPassword(password = "password", confirmPassword = "confirmPassword")
public class RegistrationRequest {

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    @NotNull( message = "Email cannot be null")
    private String email;

    @NotNull( message = "Full name cannot be null")
    private String password;

    @NotNull( message = "Full name cannot be null")
    private String confirmPassword;
}
