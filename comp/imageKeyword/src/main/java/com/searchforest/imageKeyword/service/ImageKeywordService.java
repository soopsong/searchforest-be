package com.searchforest.imageKeyword.service;

import com.searchforest.imageKeyword.repository.ImageKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageKeywordService {

    private final ImageKeywordRepository imageKeywordRepository;

}
