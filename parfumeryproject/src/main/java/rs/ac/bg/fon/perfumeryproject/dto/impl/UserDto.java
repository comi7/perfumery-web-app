package rs.ac.bg.fon.perfumeryproject.dto.impl;
import rs.ac.bg.fon.perfumeryproject.dto.Dto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Role;
/**
 *
 * @author Milica
 */
public class UserDto implements Dto {
    
    private Integer id;
    private String username;
    private String email;
    private Role role;

    public UserDto() {
    }

    public UserDto(Integer id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}