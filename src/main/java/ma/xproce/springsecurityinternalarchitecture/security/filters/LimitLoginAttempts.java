package ma.xproce.springsecurityinternalarchitecture.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LimitLoginAttempts extends OncePerRequestFilter {
    private Map<String, Integer> requestTimestamps = new HashMap<>();
    private int MAX_REQUESTS = 2;
    int attempts = 1;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        try {
            attempts = requestTimestamps.get(clientIp);
        } catch (Exception exception) {
        }
        attempts++;
        requestTimestamps.put(clientIp, attempts);
        System.out.println(" ************************************************** => The CLient Ip " + clientIp + " attemptes to connect for the " + attempts + " time");
        if (attempts > MAX_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }
        filterChain.doFilter(request, response);
    }
}


