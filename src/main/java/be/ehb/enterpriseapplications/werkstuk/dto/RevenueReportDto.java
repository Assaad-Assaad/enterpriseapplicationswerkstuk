package be.ehb.enterpriseapplications.werkstuk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueReportDto {
    private double totalRevenue;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long numberOfAuctions;
}

