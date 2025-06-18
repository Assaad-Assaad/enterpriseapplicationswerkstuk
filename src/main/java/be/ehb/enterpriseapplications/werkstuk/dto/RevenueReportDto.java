package be.ehb.enterpriseapplications.werkstuk.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Revenue report data containing totals for specific periods.")
public class RevenueReportDto {
    private double totalRevenue;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long numberOfAuctions;

    public RevenueReportDto() {}

    public RevenueReportDto(double totalRevenue, LocalDate startDate, LocalDate endDate, Long numberOfAuctions) {
        this.totalRevenue = totalRevenue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfAuctions = numberOfAuctions;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getNumberOfAuctions() {
        return numberOfAuctions;
    }

    public void setNumberOfAuctions(Long numberOfAuctions) {
        this.numberOfAuctions = numberOfAuctions;
    }
}

