package com.dulo.chat_platform.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;
}
