package be.ehb.enterpriseapplications.werkstuk.exception;

public class ResponseStatusException extends RuntimeException {
    public ResponseStatusException(String message) {
        super(message);
    }
}
