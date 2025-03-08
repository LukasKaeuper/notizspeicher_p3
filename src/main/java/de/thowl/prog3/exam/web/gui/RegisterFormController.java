package de.thowl.prog3.exam.web.gui;


import de.thowl.prog3.exam.core.AuthenticationService;
import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.gui.form.UserForm;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegisterFormController {

    @Autowired
    UserService svc;

    @Autowired
    AuthenticationService auth;

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
//        User newUser = new User();
//        newUser.setName(formdata.getUsername());
//        newUser.setPassword(formdata.getPassword());
//        newUser.setEmail(formdata.getEmail());
        try {
            Long userId = auth.register(formdata.getUsername(), formdata.getEmail(), formdata.getPassword());
            model.addAttribute("message", "Benutzer wurde erfolgreich registriert! Benutzer-ID: " + userId);
            //svc.saveUser(newUser);
        } catch (Exception e) {
            log.error("Registration failed", e);
            model.addAttribute("errorMessage", "Registrierung fehlgeschlagen!");
            return "register";
        }

        return "register";
    }
}
