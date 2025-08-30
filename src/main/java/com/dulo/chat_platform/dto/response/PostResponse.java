package com.dulo.chat_platform.dto.response;

import com.dulo.chat_platform.entity.enums.PostScope;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostResponse {
    private int id;
    private String content;
    private UserResponse user;
    private PostScope scope;
    private LocalDateTime createdAt;
}
