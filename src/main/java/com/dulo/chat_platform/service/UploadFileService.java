package com.dulo.chat_platform.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {

    String uploadImage(MultipartFile multipartFile, String uploadDir) throws IOException;

}
