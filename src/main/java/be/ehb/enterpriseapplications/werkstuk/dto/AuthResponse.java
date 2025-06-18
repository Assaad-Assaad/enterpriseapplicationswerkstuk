package be.ehb.enterpriseapplications.werkstuk.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing token or status info.")
public class AuthResponse {

    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
