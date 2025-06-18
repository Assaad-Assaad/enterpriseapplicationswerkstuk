package be.ehb.enterpriseapplications.werkstuk.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Performance data of categories, e.g., bids or revenue.")
public class CategoryPerformanceDto {
    private String categoryName;
    private Long auctionCount;
    private Double averageSalePrice;

    public CategoryPerformanceDto() {}

    public CategoryPerformanceDto(String categoryName, Long auctionCount, Double averageSalePrice) {
        this.categoryName = categoryName;
        this.auctionCount = auctionCount;
        this.averageSalePrice = averageSalePrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getAuctionCount() {
        return auctionCount;
    }

    public void setAuctionCount(Long auctionCount) {
        this.auctionCount = auctionCount;
    }

    public Double getAverageSalePrice() {
        return averageSalePrice;
    }

    public void setAverageSalePrice(Double averageSalePrice) {
        this.averageSalePrice = averageSalePrice;
    }
}