package com.searchforest.user.repository;


import com.searchforest.user.domain.PaperHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperHistoryRepository extends JpaRepository<PaperHistory, Long> {
}
