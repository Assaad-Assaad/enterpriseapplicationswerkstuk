package be.ehb.enterpriseapplications.werkstuk.service;

import be.ehb.enterpriseapplications.werkstuk.dto.RevenueReportDto;
import be.ehb.enterpriseapplications.werkstuk.repository.AuctionBidRepository;
import be.ehb.enterpriseapplications.werkstuk.repository.AuctionRepository;
import be.ehb.enterpriseapplications.werkstuk.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private AuctionBidRepository auctionBidRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void testGenerateRevenueReport() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        when(auctionBidRepository.sumTotalRevenue(startDateTime, endDateTime)).thenReturn(12000.0);
        when(auctionRepository.countByEndTimeBetween(startDateTime, endDateTime)).thenReturn(5L);

        RevenueReportDto dto = reportService.generateRevenueReport(start, end);

        assertEquals(12000.0, dto.getTotalRevenue());
        assertEquals(5L, dto.getNumberOfAuctions());
    }


    @Test
    void testExportRevenueToExcel() throws IOException {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        when(auctionBidRepository.sumTotalRevenue(startDateTime, endDateTime)).thenReturn(12000.0);
        when(auctionRepository.countByEndTimeBetween(startDateTime, endDateTime)).thenReturn(3L);

        ByteArrayInputStream stream = reportService.exportRevenueToExcel(start, end);

        assertNotNull(stream);
        assertTrue(stream.available() > 0);
    }

}
