package com.dulo.chat_platform.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorApiResponse<T> {
    private T errors;
}
