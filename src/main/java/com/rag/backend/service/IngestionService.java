package com.rag.backend.service;

import com.rag.backend.dto.request.IngestRequest;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngestionService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public void ingest(IngestRequest ingestRequest) {
        String content = null;

        if (ingestRequest.getFile() != null && !ingestRequest.getFile().isEmpty()) {
            content = readFileContent(ingestRequest.getFile());
        } else if (ingestRequest.getText() != null && !ingestRequest.getText().isBlank()) {
            content = ingestRequest.getText();
        }

        if (content == null) {
            throw new IllegalArgumentException("Either text or file must be provided");
        }

        performEmbeddingAndStorage(content, ingestRequest.getSource());
    }

    private void performEmbeddingAndStorage(String content, String source) {
        log.info("Processing content with DocumentSplitter...");

        Metadata metadata = Metadata.from(Map.of("source", source != null ? source : "text"));
        Document document = Document.from(content, metadata);

        List<TextSegment> segments = DocumentSplitters.recursive(300, 100).split(document);

        log.info("Total segments created: {}", segments.size());

        for (TextSegment segment : segments) {
            Embedding embedding = embeddingModel.embed(segment).content();
            embeddingStore.add(embedding, segment);
        }
        log.info("Ingestion completed");
    }

    private String readFileContent(MultipartFile file) {
        try {
            return new String(file.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file: " + file.getOriginalFilename(), e);
        }
    }
}