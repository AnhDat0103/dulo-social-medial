package com.dulo.chat_platform.dto.request;

import com.dulo.chat_platform.validation.MatchPassword;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@MatchPassword(password = "newPassword", confirmPassword = "confirmPassword")
public class PasswordResetRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
