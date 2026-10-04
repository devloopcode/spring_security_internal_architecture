package ma.xproce.springsecurityinternalarchitecture.security.config;

import lombok.RequiredArgsConstructor;
import ma.xproce.springsecurityinternalarchitecture.security.filters.FilterWithTimer;
import ma.xproce.springsecurityinternalarchitecture.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Access {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Security filter chain for REST API endpoints (/api/**).
     * <ul>
     *   <li>Stateless session — no HTTP session is created</li>
     *   <li>CSRF disabled (stateless APIs use tokens, not cookies)</li>
     *   <li>/api/auth/login is permitAll; all other /api/** require authentication</li>
     *   <li>JWT filter runs before UsernamePasswordAuthenticationFilter</li>
     * </ul>
     */
    @Bean
    @Order(1)
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/api/auth/login").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Security filter chain for web pages (Thymeleaf).
     * Preserves the original form-login + httpBasic flow.
     */
    @Bean
    @Order(2)
    SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        authorize -> {
                            authorize.requestMatchers("/", "/public", "/css/**",
                                    "/error", "/favicon.svg", "/toomanyrequests", "/register",
                                    "/h2-console/**").permitAll();
                            authorize.anyRequest().authenticated();
                        }
                ).formLogin(form -> {
                    form.defaultSuccessUrl("/private");
                })
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http
                .addFilterBefore(new FilterWithTimer(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Expose the AuthenticationManager bean so that the AuthController
     * can use it to authenticate login requests programmatically.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}