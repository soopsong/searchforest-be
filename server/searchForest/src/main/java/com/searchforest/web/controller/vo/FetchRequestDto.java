package com.searchforest.web.controller.vo;

import lombok.Data;

@Data
public class FetchRequestDto {

    private int startYear;
    private int endYear;
    private int maxResults = 10000;
    private int threadCount = 10;

}
