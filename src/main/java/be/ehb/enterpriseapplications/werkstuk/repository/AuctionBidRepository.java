package be.ehb.enterpriseapplications.werkstuk.repository;

import be.ehb.enterpriseapplications.werkstuk.dto.TopBidderDto;
import be.ehb.enterpriseapplications.werkstuk.model.AuctionBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AuctionBidRepository extends JpaRepository<AuctionBid, Integer> {

    List<AuctionBid> findAllByAuction_Id(int auctionId);

    @Query("SELECT NEW be.ehb.enterpriseapplications.werkstuk.dto.TopBidderDto(p.name, p.email, COUNT(b), SUM(b.price)) " +
            "FROM auction_bids b " +
            "JOIN b.person p " +
            "GROUP BY p " +
            "ORDER BY SUM(b.price) DESC")
    List<TopBidderDto> findTopBidders(int limit);

    @Query("SELECT SUM(highestBids.maxPrice) FROM (SELECT MAX(b.price) AS maxPrice " +
            "FROM auction_bids b " +
            "JOIN b.auction a " +
            "WHERE a.endTime BETWEEN :start AND :end " +
            "GROUP BY a.id) AS highestBids")
    Double sumTotalRevenue(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
