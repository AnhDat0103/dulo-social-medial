package com.dulo.chat_platform.service;

import com.dulo.chat_platform.dto.request.PostAttachmentRequest;
import com.dulo.chat_platform.dto.response.PostAttachmentResponse;

import java.util.List;

public interface AttachmentService {

    PostAttachmentResponse createAttachment(PostAttachmentRequest attachmentRequest);

    PostAttachmentResponse updateAttachment(PostAttachmentRequest attachmentRequest);

    List<PostAttachmentResponse> getAllAttachment(int postId);

    void deleteAttachment(int attachmentId);
}
