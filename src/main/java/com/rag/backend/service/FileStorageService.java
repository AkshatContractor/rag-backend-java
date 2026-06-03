package com.rag.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String upload_directory;

    public String storeFile(final MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()){
            throw new IllegalArgumentException("File cannot be empty or null.");
        }
        Path path = Paths.get(upload_directory);
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        final String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path resolved = path.resolve(fileName);
        Files.copy(file.getInputStream(), resolved, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
