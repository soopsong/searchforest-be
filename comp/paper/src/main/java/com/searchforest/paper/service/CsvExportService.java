package com.searchforest.paper.service;

import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.repository.PaperRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvExportService {
    private final PaperRepository paperRepository;

//    public void exportToCsv(String filePath) throws IOException {
//        List<Paper> papers = paperRepository.findAll();
//
//        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
//             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
//                     .withHeader("arxivId", "title", "abstractText", "categories", "published"))) {
//
//            for (Paper paper : papers) {
//                csvPrinter.printRecord(
//                        paper.getArxivId(),
//                        paper.getTitle(),
//                        paper.getAbstractText(),
//                        String.join(",", paper.getCategories()),
//                        paper.getPublished()
//                );
//            }
//
//            csvPrinter.flush();
//        }
//    }
}
