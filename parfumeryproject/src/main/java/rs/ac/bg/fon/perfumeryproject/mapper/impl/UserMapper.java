package rs.ac.bg.fon.perfumeryproject.mapper.impl;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.perfumeryproject.dto.impl.UserDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.User;
import rs.ac.bg.fon.perfumeryproject.mapper.DtoEntityMapper;
/**
 *
 * @author Marija
 */
@Component
public class UserMapper implements DtoEntityMapper<UserDto, User> {

    @Override
    public UserDto toDto(User e) {
        if (e == null) return null;
        return new UserDto(e.getId(), e.getUsername(), e.getEmail(), e.getRole());
    }

    @Override
    public User toEntity(UserDto t) {
        if (t == null) return null;
        User u = new User();
        u.setId(t.getId());
        u.setUsername(t.getUsername());
        u.setEmail(t.getEmail());
        u.setRole(t.getRole());
        return u;
    }
}