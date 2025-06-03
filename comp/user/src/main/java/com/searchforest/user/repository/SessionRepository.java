package com.searchforest.user.repository;

import com.searchforest.user.domain.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Sessions, Long> {

    Sessions findById(UUID sessionId);

    List<Sessions> findAllByUserIdOrderByUpdatedAtDesc(Long userId, Pageable pageable);
}
