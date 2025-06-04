package com.searchforest.log.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserKeywordLog {
    @Id
    @GeneratedValue
    private Long id;

    private UUID sessionId;
    private Long userId;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    private int keywordSearchCount;

    @ElementCollection
    private List<String> searchedKeywords;


}
