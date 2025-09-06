package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.request.PostAttachmentRequest;
import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.PostAttachmentResponse;
import com.dulo.chat_platform.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping()
    public ApiResponse<List<PostAttachmentResponse>> getAllAttachment(@RequestParam("post-id") int postId){
        return ApiResponse.<List<PostAttachmentResponse>>builder()
                .code("200")
                .message("Get all attachment")
                .data(attachmentService.getAllAttachment(postId))
                .build();
    }


}
