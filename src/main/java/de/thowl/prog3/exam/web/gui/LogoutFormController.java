package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.security.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class LogoutFormController {

    @Autowired
    private AuthenticationService auth;

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        try {
            String token = session.getAttribute("token").toString();
            auth.logout(token);
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Logout failed", e);
            return "error";
        }
    }
}