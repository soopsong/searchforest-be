package com.searchforest.site.dto;

import com.searchforest.keyword.domain.Keyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiKeywordResponse {
    private UUID sessionId;
    private List<KeywordResponse> group1;
    private List<KeywordResponse> group2;
    private String currentText;
}
