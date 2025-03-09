package de.thowl.prog3.exam.security.filter;

import de.thowl.prog3.exam.security.TokenService;
import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.repositories.SessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class AuthenticationFilter extends HttpFilter {

    private final TokenService tokenService;

    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = Session.currentToken;
        log.info("Token: " + token);
        if (!tokenService.isValidToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            chain.doFilter(request, response);
        }
    }
}
