package be.ehb.enterpriseapplications.werkstuk.service;

import be.ehb.enterpriseapplications.werkstuk.dto.CategoryPerformanceDto;
import be.ehb.enterpriseapplications.werkstuk.dto.RevenueReportDto;
import be.ehb.enterpriseapplications.werkstuk.dto.TopBidderDto;
import be.ehb.enterpriseapplications.werkstuk.repository.AuctionBidRepository;
import be.ehb.enterpriseapplications.werkstuk.repository.AuctionRepository;
import be.ehb.enterpriseapplications.werkstuk.repository.CategoryRepository;
import be.ehb.enterpriseapplications.werkstuk.util.ExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@CacheConfig(cacheNames = "reports")
public class ReportService {

    private final CategoryRepository categoryRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionBidRepository auctionBidRepository;


    @Autowired
    public ReportService(CategoryRepository categoryRepository, AuctionRepository auctionRepository, AuctionBidRepository auctionBidRepository) {
        this.categoryRepository = categoryRepository;
        this.auctionRepository = auctionRepository;
        this.auctionBidRepository = auctionBidRepository;
    }

    @Cacheable(value = "revenue", key = "#startDate.toString() + '-' + #endDate.toString()")
    public RevenueReportDto generateRevenueReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        Double totalRevenue = auctionBidRepository.sumTotalRevenue(start, end);
        Long numberOfAuctions = auctionRepository.countByEndTimeBetween(start, end);

        return new RevenueReportDto(
                totalRevenue != null ? totalRevenue : 0.0,
                startDate,
                endDate,
                numberOfAuctions != null ? numberOfAuctions : 0
        );
    }



    @Cacheable("topBidders")
    public List<TopBidderDto> getTopBidders(int limit) {
        return auctionBidRepository.findTopBidders(limit);
    }

    @Cacheable("categoryPerformance")
    public List<CategoryPerformanceDto> getCategoryPerformance() {
        return categoryRepository.findCategoryPerformance();
    }

    public ByteArrayInputStream exportRevenueToExcel(LocalDate startDate, LocalDate endDate) throws IOException {
        RevenueReportDto report = generateRevenueReport(startDate, endDate);
        byte[] excelBytes = ExcelExporter.exportRevenue(report);
        return new ByteArrayInputStream(excelBytes);
    }

}
