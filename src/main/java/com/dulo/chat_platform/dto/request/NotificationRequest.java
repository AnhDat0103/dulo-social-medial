package com.dulo.chat_platform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRequest {

    @NotNull(message = "Content cannot be null")
    private String content;

    private UserRequest receiver;
}
