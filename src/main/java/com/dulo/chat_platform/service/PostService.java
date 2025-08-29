package com.dulo.chat_platform.service;

import com.dulo.chat_platform.dto.request.PostPatchRequest;
import com.dulo.chat_platform.dto.request.PostRequest;
import com.dulo.chat_platform.dto.response.PostResponse;
import com.dulo.chat_platform.entity.enums.PostScope;

import java.util.List;

public interface PostService {
    PostResponse getPostById(int id);
    List<PostResponse> getAllPosts();
    List<PostResponse> getMyPosts(String email);
    List<PostResponse> getFriendPostsByEmailAndScope(int id, PostScope scope);
    PostResponse createPost(PostRequest postRequest);
    PostResponse updatePost(String email, PostPatchRequest postPatchRequest,int currentPostId);
    void deletePost(String email, int postId);
 }
