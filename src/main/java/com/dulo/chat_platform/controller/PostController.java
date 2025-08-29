package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.PostPatchRequest;
import com.dulo.chat_platform.dto.request.PostRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.PostResponse;
import com.dulo.chat_platform.entity.enums.PostScope;
import com.dulo.chat_platform.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.createPost(postRequest);
        return ApiResponse.<PostResponse>builder()
                .code("200")
                .message("Create post is successfully")
                .data(postResponse)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPostById(@PathVariable int id) {
        PostResponse postResponse = postService.getPostById(id);
        return ApiResponse.<PostResponse>builder()
                .code("200")
                .message("Get post successfully")
                .data(postResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getAllPosts(){
        List<PostResponse> posts = postService.getAllPosts();
        return ApiResponse.<List<PostResponse>>builder()
                .code("200")
                .message("Get all post successfully")
                .data(posts)
                .build();
    }

    @GetMapping("/my-posts")
    public ApiResponse<List<PostResponse>> getAllMyPosts(Authentication authentication){
        String email = authentication.getName();
        List<PostResponse> posts = postService.getMyPosts(email);
        return ApiResponse.<List<PostResponse>>builder()
                .code("200")
                .message("Get all my post successfully")
                .data(posts)
                .build();
    }

    @GetMapping("/friend-public-posts")
    public ApiResponse<List<PostResponse>> getAllFriendOnlyPosts(@RequestParam("friend-id") int id){
        List<PostResponse> posts = postService.getFriendPostsByEmailAndScope(id, PostScope.FRIEND_ONLY);
        return ApiResponse.<List<PostResponse>>builder()
                .code("200")
                .message("Get all my post successfully")
                .data(posts)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<PostResponse> updatePost(@RequestBody PostPatchRequest postPatchRequest, Authentication authentication, @PathVariable int id){
        String email = authentication.getName();
        PostResponse post = postService.updatePost(email, postPatchRequest, id);
        return ApiResponse.<PostResponse>builder()
                .code("200")
                .message("The post is updated successfully")
                .data(post)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deletePost(@PathVariable int id, Authentication authentication){
        String email = authentication.getName();
        postService.deletePost(email, id);
        return ApiResponse.builder()
                .code("200")
                .message("The post is deleted")
                .build();
    }
}
