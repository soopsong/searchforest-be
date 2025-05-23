package com.searchforest.site.dto;

import com.searchforest.user.domain.Messages;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MessageResponse {
    private LocalDateTime timestamp;
    private String content;

    public static MessageResponse from(Messages message) {
        return MessageResponse.builder()
                .content(message.getRootContent())
                .timestamp(message.getTimestamp())
                .build();
    }
}