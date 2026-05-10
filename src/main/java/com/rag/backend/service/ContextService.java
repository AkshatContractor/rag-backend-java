package com.rag.backend.service;

import com.rag.backend.dto.response.ContextResult;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContextService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    @Value("${rag.top-k:10}")
    private int defaultTopK;

    private static final int MIN_TOP_K = 1;
    private static final int MAX_TOP_K = 20;

    public ContextResult retrieve(String query, Integer requestedTopK) {

        int k = requestedTopK != null ? requestedTopK : defaultTopK;
        k = Math.max(MIN_TOP_K, Math.min(k, MAX_TOP_K));

        log.info("Using topK: {}", k);

        // 1. Embed query
        var embedding = embeddingModel.embed(query).content();

        // 2. Search in Qdrant via LangChain4j
        var results = embeddingStore.search(
                EmbeddingSearchRequest.builder()
                        .queryEmbedding(embedding)
                        .maxResults(k)
                        .build()
        );

        var matches = results.matches();

        if (matches == null || matches.isEmpty()) {
            log.warn("No relevant context found");
            return new ContextResult("", List.of());
        }

        // 3. Extract text directly (NO payload parsing needed)
        List<TextSegment> segments = matches.stream()
                .map(match -> match.embedded())
                .toList();

        String context = segments.stream()
                .map(TextSegment::text)
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining("\n\n"));

        if (context.isBlank()) {
            log.warn("Retrieved segments but no usable text");
            return new ContextResult("", List.of());
        }

        // 4. Optional: build sources
        List<Map<String, Object>> sources = segments.stream()
                .map(seg -> Map.<String, Object>of("text", seg.text()))
                .toList();

        return new ContextResult(context, sources);
    }
}