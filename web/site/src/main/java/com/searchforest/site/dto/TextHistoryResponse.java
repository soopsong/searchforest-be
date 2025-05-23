package com.searchforest.site.dto;

import com.searchforest.user.domain.TextHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class TextHistoryResponse {
    private UUID sessionId;
    private List<String> messages;

    public TextHistoryResponse(UUID sessionId, List<String> messages) {
        this.sessionId = sessionId;
        this.messages = messages;
    }
}