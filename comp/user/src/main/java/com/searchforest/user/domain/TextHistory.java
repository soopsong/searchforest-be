package com.searchforest.user.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "session_text_histories")
public class TextHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID sessionId;

    //root 검색어
    @Column(columnDefinition = "TEXT", nullable = false)
    private String rootContent;

    //child 검색어
    @ElementCollection
    @CollectionTable(name = "history_sub_contents", joinColumns = @JoinColumn(name = "message_id"))
    @OrderColumn(name = "order_index") // 순서 보장
    @Column(name = "sub_cotents", columnDefinition = "TEXT")
    private List<String> subContent;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
