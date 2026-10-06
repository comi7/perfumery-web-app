package rs.ac.bg.fon.perfumeryproject.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.perfumeryproject.dto.impl.AuthResponse;
import rs.ac.bg.fon.perfumeryproject.dto.impl.LoginRequest;
// import rs.ac.bg.fon.perfumeryproject.dto.impl.RegisterRequest;
import rs.ac.bg.fon.perfumeryproject.dto.impl.UserDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.EmailVerification;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Role;
import rs.ac.bg.fon.perfumeryproject.entity.impl.User;
import rs.ac.bg.fon.perfumeryproject.mapper.impl.UserMapper;
import rs.ac.bg.fon.perfumeryproject.repository.impl.EmailVerificationRepository;
import rs.ac.bg.fon.perfumeryproject.repository.impl.UserRepository;
import rs.ac.bg.fon.perfumeryproject.security.JwtService;
/**
 *
 * @author Milica
 */
@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final EmailVerificationRepository verificationRepo;

    public AuthService(AuthenticationManager authManager, JwtService jwt,
            UserRepository users, PasswordEncoder encoder, UserMapper userMapper,
            EmailService emailService, EmailVerificationRepository verificationRepo) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.users = users;
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.verificationRepo = verificationRepo;
    }

    // korak 1: korisnik unosi samo email, salje se verifikacioni link
    public void initiateRegistration(String email) throws Exception {
        if (users.existsByEmail(email)) {
            throw new Exception("Email already in use");
        }

        // generisi token
        String token = UUID.randomUUID().toString();

        EmailVerification ev = new EmailVerification();
        ev.setEmail(email);
        ev.setToken(token);
        ev.setExpiresAt(LocalDateTime.now().plusHours(24));
        verificationRepo.save(ev);

        emailService.sendVerificationEmail(email, token);
    }

    // korak 2: korisnik klikne na link i unosi username i password
    public UserDto completeRegistration(String token, String username, String password) throws Exception {
        EmailVerification ev = verificationRepo.findByToken(token);

        if (ev == null) throw new Exception("Invalid token");
        if (ev.isUsed()) throw new Exception("Token already used");
        if (ev.getExpiresAt().isBefore(LocalDateTime.now())) throw new Exception("Token expired");

        if (users.existsByUsername(username)) throw new Exception("Username already taken");

        User u = new User();
        u.setUsername(username);
        u.setEmail(ev.getEmail());
        u.setPasswordHash(encoder.encode(password));
        u.setRole(Role.CLIENT);
        users.save(u);

        ev.setUsed(true);
        verificationRepo.save(ev);

        return userMapper.toDto(u);
    }

    //ne koristim vise
//    public UserDto register(RegisterRequest req) throws Exception {
//        if (users.existsByUsername(req.getUsername()))
//            throw new Exception("Username already taken");
//        if (users.existsByEmail(req.getEmail()))
//            throw new Exception("Email already taken");
//
//        User u = new User();
//        u.setUsername(req.getUsername());
//        u.setEmail(req.getEmail());
//        u.setPasswordHash(encoder.encode(req.getPassword()));
//        u.setRole(Role.CLIENT);
//        users.save(u);
//        return userMapper.toDto(u);
//    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        User me = users.findByUsername(req.getUsername());
        String token = jwt.generate(
                (org.springframework.security.core.userdetails.User) auth.getPrincipal(),
                Map.of("role", me.getRole().name()));
        return new AuthResponse(token, userMapper.toDto(me));
    }
}