package be.ehb.enterpriseapplications.werkstuk.service;

import be.ehb.enterpriseapplications.werkstuk.exception.AuctionClosedException;
import be.ehb.enterpriseapplications.werkstuk.exception.FraudException;
import be.ehb.enterpriseapplications.werkstuk.exception.InsufficientBidException;
import be.ehb.enterpriseapplications.werkstuk.model.Auction;
import be.ehb.enterpriseapplications.werkstuk.model.AuctionBid;
import be.ehb.enterpriseapplications.werkstuk.model.Person;
import be.ehb.enterpriseapplications.werkstuk.repository.AuctionBidRepository;
import be.ehb.enterpriseapplications.werkstuk.repository.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    private AuctionService auctionService;
    private FakeMailService fakeMailService;

    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private AuctionBidRepository auctionBidRepository;

    Person auctioneer = new Person("123-456-789", "Test", "test@auction.com");
    Person bidder =  new Person("999-999-999", "Bidder", "bidder@auction.com");

    @BeforeEach
    void setUp() {
        fakeMailService = new FakeMailService();
        auctionService = new AuctionService(auctionRepository, auctionBidRepository, fakeMailService);
    }

    @Test
    void givenAuctionWithInvalidEndTime_whenSaveAuction_thenThrowsAuctionClosedException() {
        Auction auction = new Auction("Auction", 50.0, auctioneer, LocalDateTime.now().minusHours(2));
        assertThrows(AuctionClosedException.class, () -> auctionService.save(auction));
    }

    @Test
    void givenAuctionWithValidEndTime_whenSaveAuction_thenAuctionIsSaved() {
       Auction auction = new Auction("Auction", 50.0, auctioneer, LocalDateTime.now().plusDays(1));
       auctionService.save(auction);
        verify(auctionRepository).save(auction);
    }

    @Test
    void givenClosedAuction_whenBid_thenThrowAuctionClosedException() {
        Auction auction = new Auction("Auction", 20.0, auctioneer, LocalDateTime.now().minusDays(1));
        auction.setId(1);
        when(auctionRepository.findById(1)).thenReturn(Optional.of(auction));

        AuctionBid bid = new AuctionBid(30, auction,bidder);

        assertThrows(AuctionClosedException.class, () -> auctionService.bidOnAuction(1, bid));
    }

    @Test
    void givenBidderIsOwner_whenBid_thenThrowFraudExceptionAndSendEmail() {
        Auction auction = new Auction("Auction", 50.0, auctioneer, LocalDateTime.now().plusHours(1));
        auction.setId(1);
        when(auctionRepository.findById(1)).thenReturn(Optional.of(auction));

        AuctionBid bid = new AuctionBid(60, auction, auctioneer);
        assertThrows(FraudException.class, () -> auctionService.bidOnAuction(1, bid));
        assertEquals(1, fakeMailService.getEmails().size());
    }

    @Test
    void givenNoPreviousBidsAndBidTooLow_whenBid_thenThrowInsufficientBidException() {
        Auction auction = new Auction("Auction", 100.0, auctioneer, LocalDateTime.now().plusDays(1));
        auction.setId(1);
        when(auctionRepository.findById(1)).thenReturn(Optional.of(auction));
        when(auctionBidRepository.findAllByAuctionId(1)).thenReturn(List.of());

        AuctionBid bid = new AuctionBid(90, auction, bidder);

        assertThrows(InsufficientBidException.class, () -> auctionService.bidOnAuction(1, bid));
    }

    @Test
    void givenExistingHigherBid_whenLowerBid_thenThrowInsufficientBidException() {
        Auction auction = new Auction("Auction", 50.0, auctioneer, LocalDateTime.now().plusDays(1));
        auction.setId(1);
        when(auctionRepository.findById(1)).thenReturn(Optional.of(auction));

        AuctionBid oldBid = new AuctionBid(100, auction, new Person("888-888-888", "Old Bidder", "old@auction.com"));
        when(auctionBidRepository.findAllByAuctionId(1)).thenReturn(List.of(oldBid));

        AuctionBid newBid = new AuctionBid(90, auction, bidder);

        assertThrows(InsufficientBidException.class, () -> auctionService.bidOnAuction(1, newBid));
    }


    @Test
    void givenValidBid_whenNoConflicts_thenBidIsSaved() {
        Auction auction = new Auction("Valid Auction", 50.0, auctioneer, LocalDateTime.now().plusDays(1));
        auction.setId(1);
        when(auctionRepository.findById(1)).thenReturn(Optional.of(auction));
        when(auctionBidRepository.findAllByAuctionId(1)).thenReturn(List.of());

        AuctionBid bid = new AuctionBid(60, auction, bidder);

        auctionService.bidOnAuction(1, bid);

        verify(auctionBidRepository).save(bid);
    }




}