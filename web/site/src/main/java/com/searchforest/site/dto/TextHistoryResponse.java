package com.searchforest.site.dto;

import com.searchforest.user.domain.TextHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TextHistoryResponse {
    private LocalDateTime timestamp;
    private String content;

    public static TextHistoryResponse from(TextHistory textHistory) {
        return TextHistoryResponse.builder()
                .content(textHistory.getRootContent())
                .timestamp(textHistory.getTimestamp())
                .build();
    }
}