package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.entity.Friendship;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.FriendshipStatus;
import com.dulo.chat_platform.service.FriendShipService;
import org.springframework.data.domain.Page;


public class FriendShipServiceImpl implements FriendShipService {

    @Override
    public void sendFriendRequest(String fromEmail, String toEmail) {

    }

    @Override
    public void respondToFriendRequest(int requestId, FriendshipStatus status) {

    }

    @Override
    public Page<User> getListFriends(String email, int page, int size) {
        return null;
    }

    @Override
    public boolean areFriends(String fromEmail, String toEmail) {
        return false;
    }

    @Override
    public Page<Friendship> getReceivedRequests(String email, int page, int size) {
        return null;
    }

    @Override
    public Page<Friendship> getSentRequests(String email, int page, int size) {
        return null;
    }
}
