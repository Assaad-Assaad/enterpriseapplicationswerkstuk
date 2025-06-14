package be.ehb.enterpriseapplications.werkstuk.repository;

import be.ehb.enterpriseapplications.werkstuk.model.Auction;
import be.ehb.enterpriseapplications.werkstuk.model.AuctionBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionBidRepository extends JpaRepository<AuctionBid, Integer> {

    List<AuctionBid> findAllByAuction_Id(int auctionId);
}
