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
@Table(name = "session_messages")
public class Messages {

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
    @CollectionTable(name = "message_sub_contents", joinColumns = @JoinColumn(name = "message_id"))
    @OrderColumn(name = "order_index") // 순서 보장
    @Column(name = "sub_content", columnDefinition = "TEXT")
    private List<String> subContent;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
