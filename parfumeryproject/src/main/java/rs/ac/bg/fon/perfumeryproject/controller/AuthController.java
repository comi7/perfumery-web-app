package rs.ac.bg.fon.perfumeryproject.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.bg.fon.perfumeryproject.dto.impl.AuthResponse;
import rs.ac.bg.fon.perfumeryproject.dto.impl.CompleteRegistrationRequest;
import rs.ac.bg.fon.perfumeryproject.dto.impl.InitiateRegistrationRequest;
import rs.ac.bg.fon.perfumeryproject.dto.impl.LoginRequest;
import rs.ac.bg.fon.perfumeryproject.dto.impl.RegisterRequest;
import rs.ac.bg.fon.perfumeryproject.dto.impl.UserDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.User;
import rs.ac.bg.fon.perfumeryproject.repository.impl.UserRepository;
import rs.ac.bg.fon.perfumeryproject.service.AuthService;

/**
 *
 * @author Milica
 */

@CrossOrigin(origins = "http://localhost:3000") // frontend koji moze da mu salje zahteve
@RestController // ne vraca html stranicu vec json - sirove podatke u telu http odgovora
@RequestMapping("/api/auth") // prefiks svih ruta ovde
@Tag(name = "Auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepository users;

    public AuthController(AuthService authService, UserRepository users) {
        this.authService = authService;
        this.users = users;
    }

   

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    //trenutno ulogovani, salje ga frontendu
    @GetMapping("/me") // UserDto objekat ide u telo http odgovora - Spring ga pretvara u json format
    public ResponseEntity<UserDto> me(Authentication auth) throws Exception {
        User u = users.findByUsername(auth.getName());
        UserDto dto = new UserDto(u.getId(), u.getUsername(), u.getEmail(), u.getRole());
        return ResponseEntity.ok(dto); // pakuje u http odg (ResponseEntity), http status 200 ok
    }
    
    // POST /api/auth/initiate-registration
    @PostMapping("/initiate-registration") 
    public ResponseEntity<String> initiateRegistration(
            @Valid @RequestBody InitiateRegistrationRequest req) {
        try {
            authService.initiateRegistration(req.getEmail());
            return ResponseEntity.ok("Verification email sent. Check your inbox.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/complete-registration")
    public ResponseEntity<UserDto> completeRegistration(
            @Valid @RequestBody CompleteRegistrationRequest req) {
        try {
            UserDto dto = authService.completeRegistration(
                    req.getToken(), req.getUsername(), req.getPassword());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    // ne koristim vise
//     @PostMapping("/register")
//     public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest req) throws Exception {
//         return ResponseEntity.ok(authService.register(req));
//    }
}