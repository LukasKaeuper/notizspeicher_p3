package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.core.AuthenticationService;
import de.thowl.prog3.exam.core.entities.AccessToken;
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

    @PostMapping("/logout")
    public String logout(AccessToken token) {
        try {
            auth.logout(token);
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Logout failed", e);
            return "error"; // or handle the error appropriately
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}