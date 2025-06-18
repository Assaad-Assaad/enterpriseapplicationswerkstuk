package be.ehb.enterpriseapplications.werkstuk.util;

import be.ehb.enterpriseapplications.werkstuk.dto.ErrorResponse;
import be.ehb.enterpriseapplications.werkstuk.exception.AuctionClosedException;
import be.ehb.enterpriseapplications.werkstuk.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @Test
    void shouldReturn400ForAuctionClosedException() {

        AuctionClosedException ex = new AuctionClosedException("Test message");
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<ErrorResponse> response = handler.handleAuctionClosed(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test message", response.getBody().getMessage());
    }
}