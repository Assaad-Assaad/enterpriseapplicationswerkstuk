package be.ehb.enterpriseapplications.werkstuk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Represents an auction with start/end time, item info, and owner.")
@Entity(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "start_price", nullable = false)
    private double startPrice;

    @NotNull
    @Column(name = "end_time", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endTime;



    @JsonIgnore
    @JoinColumn(name = "person_id")
    @ManyToOne
    private Person person;


    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "auction_id")
    @JsonIgnoreProperties("auction")
    private List<AuctionBid> bids = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy ="auctions", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("auctions")
    private List<Category> categories = new ArrayList<>();

    public Auction() {
    }

    public Auction(@NotBlank String productName, @NotNull double startPrice, Person person, @NotNull LocalDateTime endTime) {
        this.productName = productName;
        this.startPrice = startPrice;
        this.person = person;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<AuctionBid> getBids() {
        return bids;
    }

    public void setBids(List<AuctionBid> bids) {
        this.bids = bids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        return id == auction.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
