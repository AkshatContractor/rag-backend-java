package com.rag.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class QueryResponse {

    private String answer;
    private List<Map<String, Object>> sources;
    private Map<String, Object> tokenUsage;
}