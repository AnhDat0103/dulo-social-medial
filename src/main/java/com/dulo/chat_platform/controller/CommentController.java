package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.CommentRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.CommentResponse;
import com.dulo.chat_platform.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentResponse> createComment(Authentication authentication, @RequestBody CommentRequest commentRequest){
        String email = authentication.getName();
        return ApiResponse.<CommentResponse>builder()
                .code("200")
                .message("The comment is created.")
                .data(commentService.createComment(email, commentRequest))
                .build();
    }
}
