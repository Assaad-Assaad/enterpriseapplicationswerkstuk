package be.ehb.enterpriseapplications.werkstuk.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopBidderDto {
    private String personName;
    private String email;
    private Long bidCount;
    private Double totalBidAmount;
}




