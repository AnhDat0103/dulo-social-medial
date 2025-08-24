package com.dulo.chat_platform.dto.request;

import com.dulo.chat_platform.validation.DateOfBirth;
import com.dulo.chat_platform.validation.MatchPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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

    @NotNull(message = "Full name cannot be null")
    private String fullName;

    @NotNull(message = "Date of birth cannot be null")
    @DateOfBirth
    private LocalDate dob;

    private String avatar;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone number must be 10 to 11 digits")
    private String phone;
}
