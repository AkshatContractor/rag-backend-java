package com.rag.backend.controller;

import com.rag.backend.dto.request.IngestRequest;
import com.rag.backend.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService ingestionService;


    @PostMapping(value = "/ingest", consumes = "multipart/form-data")
    public String ingest(@ModelAttribute IngestRequest ingestRequest) throws Exception {
        ingestionService.ingest(ingestRequest);
        return "Inserted";
    }
}
