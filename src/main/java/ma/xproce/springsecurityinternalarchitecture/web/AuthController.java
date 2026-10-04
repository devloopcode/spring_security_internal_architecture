package ma.xproce.springsecurityinternalarchitecture.web;

import lombok.RequiredArgsConstructor;
import ma.xproce.springsecurityinternalarchitecture.dto.LoginRequest;
import ma.xproce.springsecurityinternalarchitecture.dto.LoginResponse;
import ma.xproce.springsecurityinternalarchitecture.security.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that handles JWT authentication.
 *
 * <ul>
 * <li>{@code POST /api/auth/login} — Authenticates the user and returns a JWT
 * token.</li>
 * <li>{@code GET /api/auth/profile} — Returns the authenticated user's
 * information (requires a valid JWT).</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;

        /**
         * Authenticates the user with email/password and returns a JWT access token.
         *
         * @param loginRequest the login credentials
         * @return JWT token wrapped in a {@link LoginResponse}
         */
        @PostMapping("/login")
        public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getEmail(),
                                                loginRequest.getPassword()));

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String jwtToken = jwtService.generateToken(userDetails);

                return ResponseEntity.ok(new LoginResponse(jwtToken));
        }

        /**
         * Returns the authenticated user's profile information.
         * Requires a valid JWT in the Authorization header.
         */
        @GetMapping("/profile")
        public ResponseEntity<?> getProfile(Authentication authentication) {
                return ResponseEntity.ok(
                                java.util.Map.of(
                                                "username", authentication.getName(),
                                                "authorities", authentication.getAuthorities()));
        }
}
