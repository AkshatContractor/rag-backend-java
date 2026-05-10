package com.rag.backend.controller;

import com.rag.backend.config.QdrantProperties;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TestController {

    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;
    private final QdrantProperties qdrantProperties;

    @GetMapping("/test/wiring")
    public Map<String, Object> testWiring() {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("status", "UP");
        res.put("chatModelBean", chatModel.getClass().getName());
        res.put("embeddingModelBean", embeddingModel.getClass().getName());

        Map<String, Object> qdrant = new LinkedHashMap<>();
        qdrant.put("host", qdrantProperties.host());
        qdrant.put("port", qdrantProperties.port());
        qdrant.put("collectionName", qdrantProperties.collectionName());
        qdrant.put("useTls", qdrantProperties.useTls());
        qdrant.put("apiKeyPresent", qdrantProperties.apiKey() != null && !qdrantProperties.apiKey().isBlank());

        res.put("qdrant", qdrant);
        return res;
    }
}