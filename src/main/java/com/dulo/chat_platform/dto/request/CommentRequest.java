package com.dulo.chat_platform.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentRequest {
    private Integer postId = -1;
    private Integer commentParentId = -1;
    private String content;
}
