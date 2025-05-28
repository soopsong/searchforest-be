package com.searchforest.user.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sf_paperHistory")
public class PaperHistory {

    // paper history 저장용 entity
    @Id
    private String id;

    private Long userId;

    @Column(nullable = false)
    private LocalDateTime searchedAt;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(nullable = false)
    private URL url;

    @PreUpdate
    public void preUpdate() {
        this.searchedAt = LocalDateTime.now();
    }
}
