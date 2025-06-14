package be.ehb.enterpriseapplications.werkstuk.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPerformanceDto {
    private String categoryName;
    private Long auctionCount;
    private Double averageSalePrice;

}