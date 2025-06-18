package be.ehb.enterpriseapplications.werkstuk.repository;

import be.ehb.enterpriseapplications.werkstuk.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.time.LocalDateTime;


public interface AuctionRepository extends JpaRepository<Auction, Integer>, JpaSpecificationExecutor<Auction> {


    Long countByEndTimeBetween(LocalDateTime start, LocalDateTime end);


}
