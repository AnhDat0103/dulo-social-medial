package com.dulo.chat_platform.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private int id;
    private String email;
    private String fullName;
    private String avatar;
    private LocalDate dob;
    private Set<PhoneResponse> phones;

}
