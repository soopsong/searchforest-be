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
public class UserPaperLog {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private int paperSearchCount;

    @ElementCollection
    private List<String> searchedPapers;

}
