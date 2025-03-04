package de.thowl.prog3.exam.web.gui;

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
public class UserFormController {

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    UserService svc;

    @GetMapping("/user")
    public String showUserForm() {
        log.debug("entering showUserForm");
        return "userform";
    }

    @PostMapping("/user")
    public String processUserForm(Model model, UserForm formdata) {
        log.debug("entering processUserForm");
        String username = formdata.getUsername();
        String password = formdata.getPassword();
        log.debug("searching for User={}", username);
        log.debug("searching for Password={}", password);


        // retrieve user record
        String target = "userform"; // FAILURE LANE -> back to form page
        try {
            User u = this.svc.getUserWithPassword(username, password);
            if (u != null) {
                model.addAttribute("user", this.mapper.map(u));
                target = "showuser"; // SUCCESS LANE
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
        return "createuser";
    }

    @PostMapping("/register")
    public String registerUser(Model model, UserForm formdata) {
        if (formdata.getUsername().length() < 5 || formdata.getPassword().length() < 5) {
            log.error("validation error");
            model.addAttribute("errorMessage", "Fehler bei der Eingabe!, mindesens 5 Zeichen");
            return "createuser";
        }
        log.debug("entering registerUser");
        User newUser = new User();
        newUser.setName(formdata.getUsername());
        newUser.setPassword(formdata.getPassword());
        newUser.setEmail(formdata.getEmail());
        svc.saveUser(newUser);
        model.addAttribute("message", "Benutzer wurde erfolgreich registriert!");
        return "createuser";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/user";
    }

}
