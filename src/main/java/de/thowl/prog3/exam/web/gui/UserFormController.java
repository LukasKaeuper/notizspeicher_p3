package de.thowl.prog3.exam.web.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserFormController {

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    UserService svc;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/user")
    public String showUserForm() {
        log.debug("entering showUserForm");
        return "login";
    }

    @PostMapping("/user")
    public String processUserForm(Model model, UserForm formdata) {
        log.debug("entering processUserForm");
        String username = formdata.getUsername();
        String password = formdata.getPassword();
        log.debug("searching for User={}", username);


        // retrieve user record
        String target = "login"; // FAILURE LANE -> back to form page
        try {
            User u = this.svc.getUser(username);
            if (u != null && encoder.matches(password, u.getPassword())) {
                model.addAttribute("user", this.mapper.map(u));
                target = "dashboard"; // SUCCESS LANE
            }
        } catch (Exception e) {
            log.error("unable to retrieve user data");
            model.addAttribute("message", "Benutzer nicht gefunden oder Passwort falsch!");
        }
        return target;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        log.debug("entering showRegisterForm");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, UserForm formdata) {
        if (formdata.getUsername().length() < 5 || formdata.getPassword().length() < 5) {
            log.error("validation error");
            model.addAttribute("errorMessage", "Fehler bei der Eingabe!, mindesens 5 Zeichen");
            return "register";
        }
        log.debug("entering registerUser");
        User newUser = new User();
        newUser.setName(formdata.getUsername());
        newUser.setPassword(encoder.encode(formdata.getPassword()));
        newUser.setEmail(formdata.getEmail());
        svc.saveUser(newUser);
        model.addAttribute("message", "Benutzer wurde erfolgreich registriert!");
        return "register";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/user";
    }

}
