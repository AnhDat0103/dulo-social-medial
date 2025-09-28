package com.dulo.chat_platform.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponse {

    private int id;
    private int userID;
    private Integer postID;
    private String content;
    private Integer commentParentID;
}
