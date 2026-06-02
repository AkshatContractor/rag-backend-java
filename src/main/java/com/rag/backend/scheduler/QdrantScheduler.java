package com.rag.backend.scheduler;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class QdrantScheduler {

    @Value("${qdrant.api-key}")
    private String qdrant_api_key;

    @Value("${qdrant.host}")
    private String url;

    @Scheduled(cron = "0 0 10 */3 * *")
    public void qdrantPing() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + qdrant_api_key);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            String fullUrl = "https://" + url + "/collections";

            restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
            System.out.println("Successfully pinged Qdrant at: " + fullUrl);
        } catch (Exception e) {
            System.err.println("Failed to ping Qdrant: " + e.getMessage());
        }
    }
}
