package com.rag.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ContextResult {

    private final String context;
    private final List<Map<String, Object>> sources;
}
