package com.searchforest.user.repository;


import com.searchforest.user.domain.PaperHistory;
import com.searchforest.user.domain.TextHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaperHistoryRepository extends JpaRepository<PaperHistory, Long> {

    List<PaperHistory> findAllByUserIdOrderBySearchedAtDesc(Long userId);
}
