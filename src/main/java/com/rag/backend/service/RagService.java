package com.rag.backend.service;

import com.rag.backend.dto.request.QueryRequest;
import com.rag.backend.dto.response.ContextResult;
import com.rag.backend.dto.response.QueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RagService {

    private final ContextService contextService;
    private final PromptService promptService;
    private final LlmService llmService;

    public QueryResponse ask(QueryRequest request) throws Exception {

        log.info("Processing query: {}", request.getQuery());

        // 1. Retrieve context from Qdrant
        ContextResult contextResult = contextService.retrieve(
                request.getQuery(),
                request.getTopK()
        );

        // 2. Build prompt.txt
        String prompt = promptService.render(
                request.getQuery(),
                contextResult.getContext()
        );

        log.debug("Generated prompt.txt: {}", prompt);

        // 3. Call LLM
        String answer = llmService.generate(prompt);

        log.info("Generated answer successfully");

        // 4. Return response
        return new QueryResponse(
                answer,
                contextResult.getSources(),
                null
        );
    }
}