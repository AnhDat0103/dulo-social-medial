package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.PostAttachmentRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.PostAttachmentResponse;
import com.dulo.chat_platform.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentController {
    
    private final AttachmentService attachmentService;
    
    @PostMapping
    public ApiResponse<PostAttachmentResponse> createAttachment(@RequestBody PostAttachmentRequest postAttachmentRequest){
        return ApiResponse.<PostAttachmentResponse>builder()
                .code("200")
                .message("Attachment is created with post.")
                .data(attachmentService.createAttachment(postAttachmentRequest))
                .build();
    }
}
