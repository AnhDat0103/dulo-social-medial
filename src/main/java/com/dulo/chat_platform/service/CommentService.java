package com.dulo.chat_platform.service;

import com.dulo.chat_platform.dto.request.CommentRequest;
import com.dulo.chat_platform.dto.response.CommentResponse;
import org.springframework.data.domain.Page;

public interface CommentService {
    CommentResponse createComment(String email, CommentRequest commentRequest);

    Page<CommentResponse> getAllComment(int postId, int page, int size);

    CommentResponse updateComment(String email, CommentRequest commentRequest);

    void deleteComment(String email, int commentId);

    Page<CommentResponse> getAllChildComment(int commentParentId, int page, int size);
}
