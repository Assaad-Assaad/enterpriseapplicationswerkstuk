package be.ehb.enterpriseapplications.werkstuk.service;


import be.ehb.enterpriseapplications.werkstuk.exception.AuctionClosedException;
import be.ehb.enterpriseapplications.werkstuk.exception.FraudException;
import be.ehb.enterpriseapplications.werkstuk.exception.InsufficientBidException;
import be.ehb.enterpriseapplications.werkstuk.exception.ResourceException;
import be.ehb.enterpriseapplications.werkstuk.model.Auction;
import be.ehb.enterpriseapplications.werkstuk.model.AuctionBid;
import be.ehb.enterpriseapplications.werkstuk.model.Person;
import be.ehb.enterpriseapplications.werkstuk.repository.PersonRepository;
import be.ehb.enterpriseapplications.werkstuk.util.AuctionSpecifications;
import be.ehb.enterpriseapplications.werkstuk.repository.AuctionBidRepository;
import be.ehb.enterpriseapplications.werkstuk.repository.AuctionRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionBidRepository auctionBidRepository;
    private final PersonRepository personRepository;
    private final MailService mailService;
    private  final MeterRegistry meterRegistry;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository,
                          AuctionBidRepository auctionBidRepository,
                          MailService mailService,
                          MeterRegistry meterRegistry,
                          PersonRepository personRepository) {
        this.auctionRepository = auctionRepository;
        this.auctionBidRepository = auctionBidRepository;
        this.mailService = mailService;
        this.meterRegistry = meterRegistry;
        this.personRepository = personRepository;

    }


    public List<Auction> findAll() {
        return auctionRepository.findAll();
    }

    public Auction findById(int id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Auction with id: " + id + " not found"));
    }


    @CacheEvict(value = "auctions", allEntries = true)
    public Auction createAuction(Auction auction) {
        LocalDateTime now = LocalDateTime.now();
        if (auction.getEndTime().isBefore(now)) {
            throw new AuctionClosedException("The auction end time must be in the future");
        }


        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Person seller = personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceException("User not found"));


        auction.setPerson(seller);

        return auctionRepository.save(auction);
    }

    @CacheEvict(value = "auctions", allEntries = true)
    public void bidOnAuction(int auctionId, AuctionBid auctionBid) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionClosedException("Auction with id " + auctionId + " not found"));

        LocalDateTime now = LocalDateTime.now();
        if (auction.getEndTime().isBefore(now)) {
            throw new AuctionClosedException("Auction has closed");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Person bidder = personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceException("User not found"));

        if (bidder.equals(auction.getPerson())) {
            mailService.sendEmail("Fraud detected for user: "+ auction.getPerson().getName(),"fraud@auctionhouse.com");
            throw new FraudException("You cannot bid on your own auction");
        }

        List<AuctionBid> auctionBids = auctionBidRepository.findAllByAuction_Id(auctionId);

        if (auctionBids.isEmpty()) {
            if (auctionBid.getPrice() < auction.getStartPrice()){
                throw new InsufficientBidException("Bid is lower than start price");
            }
        }else {
            if (!isNewBidHigherThanAllBids(auctionBids, auctionBid)) {
                throw new InsufficientBidException("An existing bid with higher amount already exists");
            }
        }
        auctionBid.setAuction(auction);
        auctionBid.setPerson(bidder);
        auctionBidRepository.save(auctionBid);
    }


    public List<AuctionBid> findAllBidsByForAuction(int auctionId) {
        return auctionBidRepository.findAllByAuction_Id(auctionId);
    }

    @Cacheable(value = "searchAuctions", keyGenerator = "customKeyGenerator")
    public List<Auction> searchAuctions(String categoryName, Double minPrice, Double maxPrice, String status) {
        Specification<Auction> spec = Specification.allOf();

        if (categoryName != null && !categoryName.isEmpty()) {
            spec = Specification.allOf(spec, AuctionSpecifications.hasCategory(categoryName));
        }

        if (minPrice != null) {
            spec = Specification.allOf(spec, AuctionSpecifications.minPrice(minPrice));
        }

        if (maxPrice != null) {
            spec = Specification.allOf(spec, AuctionSpecifications.maxPrice(maxPrice));
        }

        if (status != null && !status.isEmpty()) {
            Boolean isActive = "ACTIVE".equalsIgnoreCase(status);
            spec = Specification.allOf(spec, AuctionSpecifications.isActive(isActive));
        }

        return auctionRepository.findAll(spec);
    }

    public List<Auction> getActiveAuctions(String status) {
        Specification<Auction> spec = Specification.allOf();

        if (status != null && !status.isEmpty()) {
            Boolean isActive = "ACTIVE".equalsIgnoreCase(status);
            spec = Specification.allOf(spec, AuctionSpecifications.isActive(isActive));
        }

        List<Auction> activeAuctions = auctionRepository.findAll(spec);
        meterRegistry.gauge("auctions.active.count", activeAuctions, List::size);

        return activeAuctions;
    }

    private boolean isNewBidHigherThanAllBids(List<AuctionBid> auctionBids, AuctionBid auctionBid) {
        double highestBid = auctionBids.stream().map(AuctionBid::getPrice).max(Double::compareTo).get();
        return auctionBid.getPrice() > highestBid;
    }

}
