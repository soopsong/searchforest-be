package com.searchforest.web.controller;

import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.service.CsvExportService;
import com.searchforest.paper.service.PaperService;
import com.searchforest.web.controller.vo.FetchRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@RestController
@RequestMapping("/admin/arxiv")
@RequiredArgsConstructor
public class AdminController {

//    private final PaperService paperService;
//    private final CsvExportService csvExportService;
//
//    /**
//     * 주제와 연도별로 arXiv 논문을 병렬로 수집하는 엔드포인트
//     * 예: POST /admin/arxiv/fetch-all?startYear=2020&endYear=2024&maxResults=10000&threadCount=10
//     */
//    @PostMapping("/fetch-all")
//    public ResponseEntity<String> fetchAll(@RequestBody FetchRequestDto request) {
//        try {
//            int savedCount = paperService.fetchAllByTopicsAndYears(
//                    request.getStartYear(),
//                    request.getEndYear(),
//                    request.getMaxResults(),
//                    request.getThreadCount()
//            );
//            return ResponseEntity.ok("✅ 총 " + savedCount + "개의 논문이 저장되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("❌ 에러 발생: " + e.getMessage());
//        }
//    }
//
//
//    @PostMapping("/enrich-all")
//    public ResponseEntity<String> enrichAll() {
//        try {
//            int enriched = paperService.enrichExistingPapersWithSemanticScholar();
//            return ResponseEntity.ok("✅ Semantic Scholar enrich 성공: " + enriched + "개 논문");
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body("❌ enrich 중 에러 발생: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/export-csv")
//    public ResponseEntity<String> exportCsv(@RequestParam String filePath) {
//        try {
//            csvExportService.exportToCsv(filePath);
//            return ResponseEntity.ok("✅ CSV 파일이 저장되었습니다: " + filePath);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError()
//                    .body("❌ CSV 저장 중 에러 발생: " + e.getMessage());
//        }
//    }
//    @GetMapping("/export-csv-direct")
//    public void exportCsvDirect(HttpServletResponse response) {
//        try {
//            List<Paper> papers = paperService.getAllPapers();
//
//            response.setContentType("text/csv");
//            response.setHeader("Content-Disposition", "attachment; filename=papers.csv");
//
//            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
//                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
//                         .withHeader("arxivId", "title", "abstractText", "categories", "published"))) {
//
//                for (Paper paper : papers) {
//                    csvPrinter.printRecord(
//                            paper.getArxivId(),
//                            paper.getTitle(),
//                            paper.getAbstractText(),
//                            String.join(",", paper.getCategories()),
//                            paper.getPublished()
//                    );
//                }
//
//                csvPrinter.flush();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("❌ CSV export 중 에러 발생: " + e.getMessage(), e);
//        }
//    }

}
