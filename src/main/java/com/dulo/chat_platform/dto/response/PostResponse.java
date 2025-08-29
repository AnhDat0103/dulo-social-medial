package com.dulo.chat_platform.dto.response;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostResponse {
    private int postId;
    private String content;
    private UserResponse userResponse;
}
