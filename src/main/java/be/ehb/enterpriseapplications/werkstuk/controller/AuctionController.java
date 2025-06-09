package be.ehb.enterpriseapplications.werkstuk.controller;

import be.ehb.enterpriseapplications.werkstuk.model.Auction;
import be.ehb.enterpriseapplications.werkstuk.model.AuctionBid;
import be.ehb.enterpriseapplications.werkstuk.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }


    @GetMapping
    List<Auction> findAll() {
        return auctionService.findAll();
    }

    @PostMapping
    ResponseEntity<Auction> save(@Valid @RequestBody Auction auction) {
      auctionService.save(auction);
      return ResponseEntity.ok(auction);
    }

    @GetMapping("{id}/bids")
    List<AuctionBid> findAllByAuctionId(@PathVariable("id") int auctionId) {
        return auctionService.findAllBidsByForAuction(auctionId);
    }

    @PostMapping("/{id}/bids")
    ResponseEntity<Void> bidOnAuction(@PathVariable("id") int auctionId, @Valid @RequestBody AuctionBid auctionBid) {
        auctionService.bidOnAuction(auctionId, auctionBid);
        return ResponseEntity.ok().build();
    }
}
