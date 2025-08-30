package com.dulo.chat_platform.dto.request;

import com.dulo.chat_platform.entity.enums.PostScope;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    PostScope scope;
}
