package be.ehb.enterpriseapplications.werkstuk.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Schema(description = "Login request containing email and password")
public class LoginDto {



    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;


    @NotBlank(message = "Password is required")
    private String password;

    public LoginDto() {}
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
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



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoginDto loginDto = (LoginDto) o;
        return email.equals(loginDto.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
