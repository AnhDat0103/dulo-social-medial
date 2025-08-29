package com.dulo.chat_platform.mapper;

import com.dulo.chat_platform.dto.request.PostAttachmentRequest;
import com.dulo.chat_platform.dto.response.PostAttachmentResponse;
import com.dulo.chat_platform.entity.PostAttachment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface PostAttachmentMapper {

    PostAttachment toPostAttachment(PostAttachmentRequest attachmentRequest);

    PostAttachmentResponse toPostAttachmentResponse(PostAttachment postAttachment);

}
