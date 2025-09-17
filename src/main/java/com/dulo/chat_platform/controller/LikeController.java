package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/totalLike/{postId}")
    public ApiResponse<Integer> getTotalLikeOfPost(@PathVariable int postId){
        return ApiResponse.<Integer>builder()
                .code("200")
                .data(likeService.countLikesOfPost(postId))
                .message("Get total like of post")
                .build();
    }

    @PostMapping("/{postId}")
    public ApiResponse<Void> handleLikeAction(@PathVariable int postId, Authentication authentication){
        String email = authentication.getName();
        likeService.handleLikePost(email, postId);
        return ApiResponse.<Void>builder()
                .code("200")
                .message("Like action is successfully.")
                .build();
    }
}
