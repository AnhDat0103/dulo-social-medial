package com.dulo.chat_platform.dto.request;

import com.dulo.chat_platform.entity.Phone;
import com.dulo.chat_platform.validation.DateOfBirth;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPatchRequest {

    @NotNull(message = "Full name cannot be null")
    private String fullName;

    @NotNull(message = "Date of birth cannot be null")
    @DateOfBirth
    private LocalDate dob;

    private String avatar;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone number must be 10 to 11 digits")
    private Set<Phone> phones;
}
