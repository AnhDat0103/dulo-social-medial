package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.PostAttachmentRequest;
import com.dulo.chat_platform.dto.response.PostAttachmentResponse;
import com.dulo.chat_platform.entity.Post;
import com.dulo.chat_platform.entity.PostAttachment;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.mapper.PostAttachmentMapper;
import com.dulo.chat_platform.repository.PostAttachmentRepository;
import com.dulo.chat_platform.repository.PostRepository;
import com.dulo.chat_platform.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final PostAttachmentRepository postAttachmentRepository;
    private final PostRepository postRepository;
    private final PostAttachmentMapper postAttachmentMapper;

    @Override
    public PostAttachmentResponse createAttachment(PostAttachmentRequest attachmentRequest) {
        Post post = postRepository.findById(attachmentRequest.getPostId()).orElseThrow(
                () -> new AppException(ErrorEnum.POST_NOT_FOUND)
        );

        if(post != null) {
            PostAttachment postAttachment = PostAttachment.builder()
                    .fileUrl(attachmentRequest.getFileUrl())
                    .fileType(attachmentRequest.getFileType())
                    .post(post)
                    .createdAt(LocalDateTime.now())
                    .build();
            return postAttachmentMapper.toPostAttachmentResponse(postAttachmentRepository.save(postAttachment));
        }
        return null;
    }

    @Override
    public PostAttachmentResponse updateAttachment(PostAttachmentRequest attachmentRequest) {
        return null;
    }

    @Override
    public List<PostAttachmentResponse> getAllAttachment(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new AppException(ErrorEnum.POST_NOT_FOUND)
        );

        return postAttachmentRepository.findAllByPost(post).stream()
                .map(postAttachmentMapper::toPostAttachmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAttachment(int attachmentId) {
        PostAttachment postAttachment = postAttachmentRepository.findById(attachmentId).orElseThrow(
                () -> new AppException(ErrorEnum.ATTACHMENT_NOT_FOUND)
        );
        postAttachmentRepository.delete(postAttachment);
    }
}
