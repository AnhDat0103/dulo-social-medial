package com.dulo.chat_platform.dto.response;

import com.dulo.chat_platform.entity.FriendshipId;
import com.dulo.chat_platform.entity.enums.FriendshipStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FriendshipResponse {
    int formUserId;
    int toUserId;
    FriendshipStatus status;
    LocalDateTime createdAt;
}
