package com.rag.backend.config;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QdrantConfig {

    @Bean
    QdrantClient qdrantClient(QdrantProperties props) {
        QdrantGrpcClient qdrantGrpcClient = QdrantGrpcClient.newBuilder(
                        props.host(),   // e.g. xxx.cloud.qdrant.io
                        props.port(),   // 6334 or 443
                        true            // TLS ON
                )
                .withApiKey(props.apiKey())
                .build();
        return new QdrantClient(qdrantGrpcClient);
    }
}
