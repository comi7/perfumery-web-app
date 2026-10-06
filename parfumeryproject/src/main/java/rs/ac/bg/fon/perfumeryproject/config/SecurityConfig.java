package rs.ac.bg.fon.perfumeryproject.config;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import rs.ac.bg.fon.perfumeryproject.security.AppUserDetailsService;
import rs.ac.bg.fon.perfumeryproject.security.JwtAuthFilter;
/**
 *
 * @author Milica
 */
@Configuration // IoC - Spring metode sa @Bean ubacuje u svoj kontekst
@EnableMethodSecurity // mogu @PreAuthorize / @Secured za veci stepen kontrole pristupa
public class SecurityConfig {
    private final JwtAuthFilter jwtFilter;
    private final AppUserDetailsService uds;

    // Dependency Injection - konstruktor ubrizgava
    public SecurityConfig(JwtAuthFilter jwtFilter, AppUserDetailsService uds) {
        this.jwtFilter = jwtFilter; // filter - presrece zahteve i proverava JWT tokene
        this.uds = uds; // ucitava podatke o user-u iz baze
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // ne treba jer ne koristim sesije preko kukija vec tokene
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // bezb. komunikacija frontend-a sa backend-om
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/logout").permitAll()
//                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-ui/index.html").permitAll()
//                .requestMatchers(HttpMethod.GET, "/api/brand/**", "/api/perfume/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/api/perfume").permitAll()
//                .requestMatchers(HttpMethod.POST, "/api/orders").hasAnyRole("CLIENT", "ADMIN")
//                .anyRequest().authenticated()
//            )
            .authorizeHttpRequests(auth -> auth
                // otvorene rute za registraciju, login i swagger dokumentaciju
                .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/logout").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-ui/index.html").permitAll()

                // svi korisnici mogu da gledaju parfeme i brendove (GET)
                .requestMatchers(HttpMethod.GET, "/api/brand/**", "/api/perfume/**", "/api/perfume", "/api/brand").permitAll()

                // klijenti i admini mogu da kreiraju porudzbine
                .requestMatchers(HttpMethod.POST, "/api/orders").hasAnyRole("CLIENT", "ADMIN")

                // admin moze crud nad brendovima
                // za svaki slucaj .hasAuthority("ADMIN") umesto .hasRole("ADMIN") jer pise ADMIN a ne ROLE_ADMIN u bazi
                .requestMatchers(HttpMethod.POST, "/api/brand/**", "/api/perfume/**", "/api/brand", "/api/perfume").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/brand/**", "/api/perfume/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/brand/**", "/api/perfume/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                // za sve ostalo - mora ulogovan user
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of( // salje zahteve ovom api-ju
            "http://localhost:3000"
        ));
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        cfg.setExposedHeaders(List.of("Authorization")); // da frontend procita zaglavlje iz odgovora servera - korisno kad mu server salje novi token
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // alg. za hesovanje lozinki pre nego sto se sacuvaju u bazu
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Spring security manager
    }
}
