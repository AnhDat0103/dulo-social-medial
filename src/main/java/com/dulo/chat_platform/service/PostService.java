package com.dulo.chat_platform.service;

import com.dulo.chat_platform.dto.request.PostPatchRequest;
import com.dulo.chat_platform.dto.request.PostRequest;
import com.dulo.chat_platform.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;



public interface PostService {
    PostResponse getPostById(int id);
    Page<PostResponse> getAllPosts(int page, int size);
    Page<PostResponse> getMyPosts(String email, int page, int size);
    Page<PostResponse> getFriendPostsByFriendIdAndScope(String viewerId, int friendId, int page, int size);
    PostResponse createPost(PostRequest postRequest, String email);
    PostResponse updatePost(String email, PostPatchRequest postPatchRequest,int currentPostId);
    void deletePost(String email, int postId);
 }
