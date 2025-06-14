package be.ehb.enterpriseapplications.werkstuk.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity(name = "persons")
public class Person {
    @Id
    @Column(name = "auction_person_number", unique = true, nullable = false, updatable = false)
    @AuctionPersonNumber
    private String auctionPersonNumber;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Email address is not valid")
    @Column(name = "email_address", nullable = false, unique = true)
    private String email;

    public Person() {}

    public Person(String auctionPersonNumber, @NotBlank String name, @Email String emailAddress) {
        this.auctionPersonNumber = auctionPersonNumber;
        this.name = name;
        this.email = emailAddress;
    }

    public String getAuctionPersonNumber() {
        return auctionPersonNumber;
    }

    public void setAuctionPersonNumber(String auctionPersonNumber) {
        this.auctionPersonNumber = auctionPersonNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return auctionPersonNumber.equals(person.auctionPersonNumber);
    }

    @Override
    public int hashCode() {
        return auctionPersonNumber.hashCode();
    }
}
