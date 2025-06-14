package be.ehb.enterpriseapplications.werkstuk.repository;

import be.ehb.enterpriseapplications.werkstuk.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AuctionRepository extends JpaRepository<Auction, Integer>, JpaSpecificationExecutor<Auction> { }
