package com.dulo.chat_platform.service;

public interface LikeService {
    void handleLikePost(String email, int postId);

    int countLikesOfPost(int postId);
}
