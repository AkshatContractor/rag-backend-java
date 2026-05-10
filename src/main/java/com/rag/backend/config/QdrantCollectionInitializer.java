package com.rag.backend.config;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QdrantCollectionInitializer {

    private static final long VECTOR_SIZE = 3072L;

    @Bean
    public ApplicationRunner ensureQdrantCollection(QdrantClient qdrantClient, QdrantProperties props) {
        return args -> {
            String collectionName = props.collectionName();

            Boolean exists = qdrantClient.collectionExistsAsync(collectionName).get();
            if (Boolean.TRUE.equals(exists)) {
                log.info("Qdrant collection '{}' already exists", collectionName);
                return;
            }

            Collections.VectorParams vectorParams = Collections.VectorParams.newBuilder()
                    .setSize(VECTOR_SIZE)
                    .setDistance(Collections.Distance.Cosine)
                    .build();

            Collections.VectorsConfig vectorsConfig = Collections.VectorsConfig.newBuilder()
                    .setParams(vectorParams)
                    .build();

            Collections.CreateCollection createCollection = Collections.CreateCollection.newBuilder()
                    .setCollectionName(collectionName)
                    .setVectorsConfig(vectorsConfig)
                    .build();

            qdrantClient.createCollectionAsync(createCollection).get();
            log.info("Created Qdrant collection '{}' with size={} distance=COSINE", collectionName, VECTOR_SIZE);
        };
    }
}