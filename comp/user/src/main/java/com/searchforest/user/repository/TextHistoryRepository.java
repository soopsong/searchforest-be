package com.searchforest.user.repository;

import com.searchforest.user.domain.TextHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TextHistoryRepository extends JpaRepository<TextHistory, Long> {

    List<TextHistory> findBySessionIdOrderByTimestampAsc(UUID sessionId);

    Optional<TextHistory> findBySessionId(UUID sessionId);

    Optional<TextHistory> findFirstBySessionIdOrderByTimestampAsc(UUID sessionId);

}
