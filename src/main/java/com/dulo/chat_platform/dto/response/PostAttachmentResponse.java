package com.dulo.chat_platform.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostAttachmentResponse {
    private int id;
    private String fileUrl;
    private PostResponse post;
}
