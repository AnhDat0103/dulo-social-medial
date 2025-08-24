package com.dulo.chat_platform.dto.request;

import com.dulo.chat_platform.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostRequest {

    @NotNull(message = "Content cannot be null")
    private String content;

    private User user;
}
