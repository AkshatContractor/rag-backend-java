package com.rag.backend.controller;

import com.rag.backend.dto.request.QueryRequest;
import com.rag.backend.dto.response.ApiResponse;
import com.rag.backend.dto.response.QueryResponse;
import com.rag.backend.service.RagService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class RagController {

    private final RagService ragService;

    @PostMapping("/ask")
    @RateLimiter(name = "publicApi")
    public ResponseEntity<ApiResponse<QueryResponse>> askQuestion(@RequestBody QueryRequest request) {
        try {
            QueryResponse answer = ragService.ask(request);
            return ResponseEntity.ok(ApiResponse.success(answer, "Query processed successfully"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate response: " + e.getMessage());
        }
    }
}