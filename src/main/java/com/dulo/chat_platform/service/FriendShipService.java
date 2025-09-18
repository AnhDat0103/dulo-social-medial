package com.dulo.chat_platform.service;


import com.dulo.chat_platform.entity.Friendship;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.FriendshipStatus;
import org.springframework.data.domain.Page;

public interface FriendShipService {

    void sendFriendRequest(String fromEmail, String toEmail);

    void respondToFriendRequest(int requestId, FriendshipStatus status);

    Page<User> getListFriends(String email,  int page, int size);

    boolean areFriends(String fromEmail, String toEmail);

    Page<Friendship> getReceivedRequests(String email, int page, int size);

    Page<Friendship> getSentRequests(String email, int page, int size);
}
