package be.ehb.enterpriseapplications.werkstuk.controller;

import be.ehb.enterpriseapplications.werkstuk.dto.CategoryPerformanceDto;
import be.ehb.enterpriseapplications.werkstuk.dto.RevenueReportDto;
import be.ehb.enterpriseapplications.werkstuk.dto.TopBidderDto;
import be.ehb.enterpriseapplications.werkstuk.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }



    @GetMapping("/revenue")
    public ResponseEntity<RevenueReportDto> getRevenueReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.generateRevenueReport(startDate, endDate));
    }

    @GetMapping("/top-bidders")
    public ResponseEntity<List<TopBidderDto>> getTopBidders(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(reportService.getTopBidders(limit));
    }

    @GetMapping("/category-performance")
    public ResponseEntity<List<CategoryPerformanceDto>> getCategoryPerformance() {
        return ResponseEntity.ok(reportService.getCategoryPerformance());
    }

    @GetMapping("/revenue/excel")
    public ResponseEntity<InputStreamResource> exportRevenueToExcel(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws IOException {
        ByteArrayInputStream stream = reportService.exportRevenueToExcel(startDate, endDate);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Revenue_Report.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }
}

