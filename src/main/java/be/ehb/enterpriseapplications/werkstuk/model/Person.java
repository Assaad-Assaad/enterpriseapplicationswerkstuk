package be.ehb.enterpriseapplications.werkstuk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Schema(description = "Represents a person participating in the system.")
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

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public Person() {}

    public Person(String auctionPersonNumber, @NotBlank String name, @Email String email, @NotBlank String password) {
        this.auctionPersonNumber = auctionPersonNumber;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
