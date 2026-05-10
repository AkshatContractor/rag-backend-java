package com.rag.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "qdrant")
public record QdrantProperties(
        String host,
        Integer port,
        String apiKey,
        String collectionName,
        Boolean useTls
) {}