package de.thowl.prog3.exam.security.filter;

import de.thowl.prog3.exam.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

/**
 * Die Klasse AuthenticationFilter ist für die Authentifizierung der Anfragen zustaendig.
 * Sie ueberprueft, ob ein gueltiger SessionToken für die geschuetzen URLs vorhanden ist.
 * Diese Klasse wurde mit Unterstuetzung von Copilot geschrieben.
 */
@Slf4j
public class AuthenticationFilter extends HttpFilter {

    private final SessionService sessionService;

    public AuthenticationFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String token = session.getAttribute("token").toString();
        if (!sessionService.isValidToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            chain.doFilter(request, response);
        }
    }
}
