package com.rag.backend.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class IngestRequest {
    private String text;
    private MultipartFile file;
    private String source;
}
