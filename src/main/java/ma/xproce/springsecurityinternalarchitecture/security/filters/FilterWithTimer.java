package ma.xproce.springsecurityinternalarchitecture.security.filters;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


public class FilterWithTimer extends OncePerRequestFilter {

    private static class LoginAttempt {
        private int attempts;
        private long lastAttemptTime;

        public LoginAttempt(int attempts, long lastAttemptTime) {
            this.attempts = attempts;
            this.lastAttemptTime = lastAttemptTime;
        }
    }

    private Map<String, LoginAttempt> loginAttempts = new HashMap<>();
    private int MAX_REQUESTS = 5;
    private long TIMEOUT_SECONDS = 60;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        boolean page = request.getRequestURI().contains("/login") ? true : false;

        if (page) {
            long currentTime = System.currentTimeMillis();

            LoginAttempt attempt = loginAttempts.getOrDefault(clientIp, new LoginAttempt(0, currentTime));

            loginAttempts.put(clientIp, attempt);
            attempt.attempts++; // Increment attempts on every login request
            // Check if the elapsed time exceeds the timeout
            if (currentTime - attempt.lastAttemptTime > TIMEOUT_SECONDS * 1000) {
                attempt.attempts = 0;
            }

            attempt.lastAttemptTime = currentTime; // Update last attempt time
            loginAttempts.put(clientIp, attempt);

            System.out.println(" ************************************************** => " +
                    "The CLient Ip " + clientIp + " attemptes to connect for the " + attempt.attempts + " time");

            if (attempt.attempts > MAX_REQUESTS) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                request.getRequestDispatcher("/toomanyrequests").forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}