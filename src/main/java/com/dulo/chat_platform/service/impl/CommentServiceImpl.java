package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.CommentRequest;
import com.dulo.chat_platform.dto.response.CommentResponse;
import com.dulo.chat_platform.entity.Comment;
import com.dulo.chat_platform.entity.Post;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.repository.CommentRepository;
import com.dulo.chat_platform.repository.PostRepository;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentResponse createComment(String email, CommentRequest commentRequest) {
        Post post = null;
        Comment parentComment = null;

        User user = userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);

        if(commentRequest.getPostId() != -1){
             post = postRepository.findById(commentRequest.getPostId()).orElseThrow(() -> new AppException(ErrorEnum.POST_NOT_FOUND));
        }

        if(commentRequest.getCommentParentId() != -1){
             parentComment = commentRepository.findById(commentRequest.getCommentParentId()).orElseThrow(() -> new AppException(ErrorEnum.COMMENT_NOT_FOUND));
             post = parentComment.getPost();
        }

        Comment newComment = Comment.builder()
                .content(commentRequest.getContent())
                .user(user)
                .post(post)
                .parentComment(parentComment)
                .build();

        commentRepository.save(newComment);

        return CommentResponse.builder()
                .commentParentID(parentComment != null ? parentComment.getId() : null)
                .content(commentRequest.getContent())
                .postID(post != null ? post.getId() : null)
                .userID(user.getId())
                .build();
    }

    @Override
    public Page<CommentResponse> getAllComment(int postId, int page, int size) {
        return null;
    }

    @Override
    public CommentResponse updateComment(String email, CommentRequest commentRequest) {
        return null;
    }

    @Override
    public void deleteComment(String email, int commentId) {

    }

    @Override
    public Page<CommentResponse> getAllChildComment(int commentParentId, int page, int size) {
        return null;
    }
}
