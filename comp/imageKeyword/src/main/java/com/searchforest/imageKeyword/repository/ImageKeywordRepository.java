package com.searchforest.imageKeyword.repository;

import com.searchforest.imageKeyword.domain.ImageKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageKeywordRepository extends JpaRepository<ImageKeyword,Long> {

    Optional<ImageKeyword> findByText(String text);
}
