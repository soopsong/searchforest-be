package com.searchforest.site.dto;

import com.searchforest.user.domain.Sessions;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class SessionResponse {
//    private UUID sessionId;
//    private String title;
//    private LocalDateTime updatedAt;
//
//    public static SessionResponse from(Sessions session) {
//        return SessionResponse.builder()
//                .sessionId(session.getId())
//                .updatedAt(session.getUpdatedAt())
//                .build();
//    }
}