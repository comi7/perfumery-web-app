package rs.ac.bg.fon.perfumeryproject.dto.impl;
import jakarta.validation.constraints.NotBlank;
/**
 *
 * @author Milica
 */
public class LoginRequest {
    
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}