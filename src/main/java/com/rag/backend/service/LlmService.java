package com.rag.backend.service;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LlmService {

    private final ChatModel chatModel;

    public String generate(String prompt) throws Exception {

        log.info("Calling Gemini...");

        ChatResponse response = chatModel.chat(
                UserMessage.from(prompt)
        );

        String answer = response.aiMessage().text();

        if (answer == null || answer.isBlank()) {
            throw new RuntimeException("LLM returned empty response");
        }

        return answer.trim();
    }
}