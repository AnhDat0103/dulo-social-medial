package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    @Override
    public String uploadImage(MultipartFile file, String uploadDir) throws IOException {
        if(file.isEmpty()){
            throw new IOException("Faile to upload file");
        }

        // kiem tra duong dan co ton tai hay khong, neu ko ton tai thi ta se tao dir
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // dat ten cho file tranh su trung lap
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName); // tap ra file path  -> dir-name/file-name

        // luu file ra local
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

}
