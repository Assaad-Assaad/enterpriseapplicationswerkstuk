package be.ehb.enterpriseapplications.werkstuk.model;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AuctionSpecifications {

    public static Specification<Auction> hasCategory(String categoryName) {
        return (root, query, cb) -> {
            Join<Auction, Category> categories = root.join("categories");
            return cb.equal(categories.get("name"), categoryName);
        };
    }

    public static Specification<Auction> minPrice(Double minPrice) {
        return (root, query, cb) -> {
            Subquery<Double> highestBid = query.subquery(Double.class);
            Root<AuctionBid> bidRoot = highestBid.from(AuctionBid.class);

            Expression<Double> pricePath = bidRoot.get("price").as(Double.class);

            highestBid.select(cb.greatest(pricePath))
                    .where(cb.equal(root.get("id"), bidRoot.get("auction").get("id")));

            Expression<Double> currentPrice = cb.coalesce(highestBid.getSelection(), root.get("startPrice").as(Double.class));
            return cb.greaterThanOrEqualTo(currentPrice, minPrice);
        };
    }

    public static Specification<Auction> maxPrice(Double maxPrice) {
        return (root, query, cb) -> {
            Subquery<Double> highestBid = query.subquery(Double.class);
            Root<AuctionBid> bidRoot = highestBid.from(AuctionBid.class);

            Expression<Double> pricePath = bidRoot.get("price").as(Double.class);

            highestBid.select(cb.greatest(pricePath))
                    .where(cb.equal(root.get("id"), bidRoot.get("auction").get("id")));

            Expression<Double> currentPrice = cb.coalesce(highestBid.getSelection(), root.get("startPrice").as(Double.class));
            return cb.lessThanOrEqualTo(currentPrice, maxPrice);
        };
    }


    public static Specification<Auction> isActive(Boolean active) {
        return (root, query, cb) -> {
            LocalDateTime now = LocalDateTime.now();
            if (active) {
                return cb.greaterThan(root.get("endTime"), now);
            } else {
                return cb.lessThanOrEqualTo(root.get("endTime"), now);
            }
        };
    }

}
