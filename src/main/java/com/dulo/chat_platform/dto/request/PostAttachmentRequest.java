package com.dulo.chat_platform.dto.request;

import com.dulo.chat_platform.entity.enums.FileType;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostAttachmentRequest {

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private int postId;

}
