package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.PostPatchRequest;
import com.dulo.chat_platform.dto.request.PostRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.PostResponse;
import com.dulo.chat_platform.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest postRequest, Authentication authentication) {
        String email = authentication.getName();
        PostResponse postResponse = postService.createPost(postRequest, email);
        return ApiResponse.<PostResponse>builder()
                .code("200")
                .message("Create post is successfully")
                .data(postResponse)
                .build();
    }

    @GetMapping("/{id:\\d+}")
    public ApiResponse<PostResponse> getPostById(@PathVariable int id) {
        PostResponse postResponse = postService.getPostById(id);
        return ApiResponse.<PostResponse>builder()
                .code("200")
                .message("Get post successfully")
                .data(postResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<Page<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<PostResponse> posts = postService.getAllPosts(page, size);
        return ApiResponse.<Page<PostResponse>>builder()
                .code("200")
                .message("Get all post successfully")
                .data(posts)
                .build();
    }

    @GetMapping("/my-posts")
    public ApiResponse<Page<PostResponse>> getAllMyPosts(Authentication authentication,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size){
        String email = authentication.getName();
        Page<PostResponse> posts = postService.getMyPosts(email, page, size);
        return ApiResponse.<Page<PostResponse>>builder()
                .code("200")
                .message("Get all my post successfully")
                .data(posts)
                .build();
    }

    @GetMapping("/friend-public-posts")
    public ApiResponse<Page<PostResponse>> getAllFriendOnlyPosts(
            @RequestParam("friend-id") int friendId,
            @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size, Authentication authentication){
        String emailViewer = authentication.getName();
        Page<PostResponse> posts = postService.getFriendPostsByFriendIdAndScope(emailViewer,friendId, page, size);
        return ApiResponse.<Page<PostResponse>>builder()
                .code("200")
                .message("Get all friend post successfully")
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
