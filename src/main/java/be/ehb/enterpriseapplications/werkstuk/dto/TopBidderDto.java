package be.ehb.enterpriseapplications.werkstuk.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing the top bidder and their bid amount.")
public class TopBidderDto {
    private String personName;
    private String email;
    private Long bidCount;
    private Double totalBidAmount;

    public TopBidderDto() {}

    public TopBidderDto(String personName, String email, Long bidCount, Double totalBidAmount) {
        this.personName = personName;
        this.email = email;
        this.bidCount = bidCount;
        this.totalBidAmount = totalBidAmount;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBidCount() {
        return bidCount;
    }

    public void setBidCount(Long bidCount) {
        this.bidCount = bidCount;
    }

    public Double getTotalBidAmount() {
        return totalBidAmount;
    }

    public void setTotalBidAmount(Double totalBidAmount) {
        this.totalBidAmount = totalBidAmount;
    }
}




