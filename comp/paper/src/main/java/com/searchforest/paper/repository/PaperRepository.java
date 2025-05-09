package com.searchforest.paper.repository;

import com.searchforest.paper.domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaperRepository extends JpaRepository<Paper, Long> {
    boolean existsByArxivId(String arxivId);

    @Query("SELECT p.arxivId FROM Paper p")
    List<String> findAllArxivIds();


    Optional<Paper> findByTitle(String title);
}
