package com.rag.backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class QueryRequest {

    @Size(min = 1, max = 100, message = "query size must be bw 3 and 100 words")
    @NotBlank(message = "not blank")
    private String query;

    @Min(value = 1, message = "topk must be atleat 1")
    @Max(value = 20, message = "topk must be below 20")
    private Integer topK;
}
