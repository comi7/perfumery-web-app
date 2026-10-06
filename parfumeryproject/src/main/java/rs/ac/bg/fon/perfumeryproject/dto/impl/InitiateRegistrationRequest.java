package rs.ac.bg.fon.perfumeryproject.dto.impl;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author Milica
 */
public class InitiateRegistrationRequest {
    

    @NotBlank @Email
    private String email;

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}