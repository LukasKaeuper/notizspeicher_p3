package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.core.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.gui.form.UserForm;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginFormController {

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    UserService svc;

    @Autowired
    AuthenticationService auth;

    @GetMapping("/login")
    public String showUserForm() {
        log.debug("entering Login");
        return "login";
    }

    @PostMapping("/login")
    public String processUserForm(Model model, UserForm formdata) {
        log.debug("entering processLoginForm");
        String username = formdata.getUsername();
        String password = formdata.getPassword();
        log.debug("searching for User={}", username);
        log.debug("searching for Password={}", password);

        // retrieve user record
        String target = "login"; // FAILURE LANE -> back to form page
        try {
            User u = this.svc.getUserWithPassword(username, password);
            if (this.auth.isValid(username, password)) {
                model.addAttribute("user", this.mapper.map(u));
                log.debug("User is valid, attempting to login");
                auth.login(username, password);
                target = "dashboard"; // SUCCESS LANE
            }
        } catch (Exception e) {
            log.error("unable to retrieve user data");
            model.addAttribute("message", "Benutzer nicht gefunden oder Passwort falsch!");
        }
        return target;
    }
}