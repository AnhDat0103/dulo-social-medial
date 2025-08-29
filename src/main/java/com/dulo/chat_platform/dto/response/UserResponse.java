package com.dulo.chat_platform.dto.response;

import com.dulo.chat_platform.entity.Phone;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private int userId;
    private String email;
    private String fullName;
    private String avatar;
    private LocalDate dob;
    private Set<PhoneResponse> phones;

}
