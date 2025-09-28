package com.dulo.chat_platform.service;


import com.dulo.chat_platform.dto.response.FriendshipResponse;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.entity.FriendshipId;
import com.dulo.chat_platform.entity.enums.FriendshipStatus;
import org.springframework.data.domain.Page;

public interface FriendShipService {

    void sendFriendRequest(String fromEmail, int toUserId);

    FriendshipResponse respondToFriendRequest(FriendshipId requestId, FriendshipStatus status);

    Page<UserResponse> getListFriends(String email, int page, int size);

    boolean areFriends(String fromEmail, int friendId);

    Page<FriendshipResponse> getReceivedRequests(String email, int page, int size);
}
