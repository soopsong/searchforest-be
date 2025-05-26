package com.searchforest.site.dto;

import com.searchforest.user.domain.TextHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TextHistoryResponse {
    private UUID sessionId;
    private String rootMessages;
    private List<String> subMessages;

}