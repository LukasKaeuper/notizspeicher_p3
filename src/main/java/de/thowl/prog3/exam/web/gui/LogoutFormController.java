package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.security.AuthenticationService;
import de.thowl.prog3.exam.security.entities.AccessToken;
import de.thowl.prog3.exam.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class LogoutFormController {

    @Autowired
    private AuthenticationService auth;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/logout")
    public String logout(AccessToken token) {
        try {
            auth.logout(token);
            sessionService.setCurrentToken(null);
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Logout failed", e);
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}