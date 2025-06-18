package be.ehb.enterpriseapplications.werkstuk.dto;

import be.ehb.enterpriseapplications.werkstuk.model.AuctionPersonNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Registration request payload including user info and credentials.")
public class RegisterDto {

    @AuctionPersonNumber
    @NotBlank
    private String auctionPersonNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;


    public RegisterDto() {}

    public RegisterDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getAuctionPersonNumber() {
        return auctionPersonNumber;
    }
    public void setAuctionPersonNumber(String auctionPersonNumber) {
        this.auctionPersonNumber = auctionPersonNumber;
    }
}
