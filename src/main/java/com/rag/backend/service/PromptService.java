package com.rag.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class PromptService {

    private final String template;

    public PromptService() throws Exception {
        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("prompt.txt")) {

            if (is == null) {
                throw new RuntimeException("prompt.txt.txt not found in resources");
            }

            template = new String(is.readAllBytes());
            log.info("Prompt template loaded");
        }
    }

    public String render(String question, String context) {
        return template
                .replace("{question}", question)
                .replace("{context}", context);
    }
}