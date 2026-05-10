package com.rag.backend.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LangChainConfig {

    @Bean
    ChatModel chatModel(
            @Value("${langchain4j.google-ai.api-key}") String apiKey,
            @Value("${langchain4j.google-ai.chat-model.model-name}") String modelName,
            @Value("${langchain4j.google-ai.chat-model.temperature:0.7}") Double temperature
    ) {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(temperature)
                .build();
    }

    @Bean
    EmbeddingModel embeddingModel(
            @Value("${langchain4j.google-ai.api-key}") String apiKey,
            @Value("${langchain4j.google-ai.embedding-model.model-name}") String modelName
    ) {
        log.info("Loading embedding model {}",  modelName);
        return GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(
            QdrantClient qdrantClient,
            @Value("${qdrant.collection-name}") String collectionName
    ) {
        log.info("Initializing Qdrant store with collection: {}", collectionName);
        return QdrantEmbeddingStore.builder()
                .client(qdrantClient)
                .collectionName(collectionName)
                .payloadTextKey("text")
                .build();
    }
}