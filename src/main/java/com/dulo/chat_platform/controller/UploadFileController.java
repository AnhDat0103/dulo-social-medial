package com.dulo.chat_platform.controller;


import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.service.UploadFileService;
import com.dulo.chat_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class UploadFileController {

    private final UploadFileService uploadFileService;
    private final UserService userService;

    @PostMapping("/avatar")
    public ApiResponse<?> uploadImage(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
        String email = authentication.getName();
        String uploadDir = "src/main/resources/static/images/avatars";
        String fileName = uploadFileService.uploadImage(file, uploadDir);
        userService.uploadAvatar(email,fileName);
        return ApiResponse.builder()
                .code("200")
                .message("Avatar is uploaded.")
                .build();

    }
}
