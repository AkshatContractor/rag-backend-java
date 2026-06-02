package com.rag.backend.controller;

import com.rag.backend.dto.request.IngestRequest;
import com.rag.backend.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService ingestionService;

    @Value("${app.ingest.api-key}")
    private String apiKey;

    @PostMapping(value = "/ingest", consumes = "multipart/form-data")
    public ResponseEntity<String> ingest(
            @RequestHeader(name = "X-API-KEY", required = false) String key,
            @ModelAttribute IngestRequest ingestRequest
    ) throws Exception {

        if (key == null || !key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or missing API key");
        }

        ingestionService.ingest(ingestRequest);
        return ResponseEntity.ok("Inserted");
    }
}