package be.ehb.enterpriseapplications.werkstuk.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuctionPersonNumberValidatorTest {

    static AuctionPersonNumberValidator validator;

    @BeforeAll
    static void init() {
        validator = new AuctionPersonNumberValidator();
    }

    @Test
    void givenValidAuctionPersonNumber_whenValidate_thenTrue() {
        String auctionPersonNumber = "123-456-789";
        boolean valid = validator.isValid(auctionPersonNumber, null);
        assertTrue(valid);
    }

    @Test
    void givenEmptyString_whenValidate_thenFalse() {
        String auctionPersonNumber = "";
        boolean valid = validator.isValid(auctionPersonNumber, null);
        assertFalse(valid);
    }

    @Test
    void givenInvalidCharacter_whenValidate_thenFalse() {
        String auctionPersonNumber = "123.456-789";
        boolean valid = validator.isValid(auctionPersonNumber, null);
        assertFalse(valid);
    }
    @Test
    void givenLetterWithDigit_whenValidate_thenFalse() {
        String auctionPersonNumber = "1A3-456-789";
        boolean valid = validator.isValid(auctionPersonNumber, null);
        assertFalse(valid);
    }

    @Test
    void givenShortAuctionPersonNumber_whenValidate_thenFalse() {
        String auctionPersonNumber = "123-456-7";
        boolean valid = validator.isValid(auctionPersonNumber, null);
        assertFalse(valid);
    }

}