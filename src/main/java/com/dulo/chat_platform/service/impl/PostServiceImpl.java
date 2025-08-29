package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.PostPatchRequest;
import com.dulo.chat_platform.dto.request.PostRequest;
import com.dulo.chat_platform.dto.response.PostResponse;
import com.dulo.chat_platform.entity.enums.PostScope;
import com.dulo.chat_platform.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Override
    public PostResponse getPostById(int id) {
        return null;
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return List.of();
    }

    @Override
    public List<PostResponse> getMyPosts(String email) {
        return List.of();
    }

    @Override
    public List<PostResponse> getFriendPostsByEmailAndScope(int id, PostScope scope) {
        return List.of();
    }

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        return null;
    }

    @Override
    public PostResponse updatePost(String email, PostPatchRequest  postPatchRequest, int currentPostId) {
        return null;
    }

    @Override
    public void deletePost(String email, int postId) {

    }
}
