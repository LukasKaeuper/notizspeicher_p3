package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.security.AuthenticationService;
import de.thowl.prog3.exam.web.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
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
    public String showLoginForm() {
        log.debug("entering Login");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(Model model, UserForm formdata, HttpSession session) {
        log.debug("entering processLoginForm");
        String username = formdata.getUsername();
        String password = formdata.getPassword();
        String target = "login"; // FAILURE LANE -> back to form page
        try {
            if (this.auth.isValid(username, password)) {
                User u = this.svc.getUserWithPassword(username, password);
                UserDTO userDTO = this.mapper.map(u);
                session.setAttribute("user", userDTO);
                session.setAttribute("userId", u.getId());
                log.debug("User is valid, attempting to login");
                auth.login(username, password);
                target = "redirect:/dashboard"; // SUCCESS LANE
            } else {
                log.error("invalid user data");
                model.addAttribute("message", "Benutzer nicht gefunden oder Passwort falsch!");
            }
        } catch (Exception e) {
            log.error("unable to retrieve user data");
            model.addAttribute("message", "Benutzer nicht gefunden oder Passwort falsch!");
        }
        return target;
    }
}