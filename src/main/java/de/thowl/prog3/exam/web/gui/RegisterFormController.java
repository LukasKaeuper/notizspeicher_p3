package de.thowl.prog3.exam.web.gui;


import de.thowl.prog3.exam.security.AuthenticationService;
import de.thowl.prog3.exam.web.gui.form.UserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegisterFormController {

    @Autowired
    AuthenticationService auth;

    @GetMapping("/register")
    public String showRegisterForm() {
        log.debug("entering showRegisterForm");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, UserForm formdata) {
        switch (auth.register(formdata.getUsername(), formdata.getEmail(), formdata.getPassword())) {
            case "validation error":
                model.addAttribute("errorMessage", "Fehler bei der Eingabe!, mindestens 5 Zeichen");
                break;
            case "user registered":
                model.addAttribute("message", "Benutzer wurde erfolgreich registriert!");
                break;
            case "registration failed":
                model.addAttribute("errorMessage", "Registrierung fehlgeschlagen!");
                break;
            case "duplicate error":
                model.addAttribute("errorMessage", "Registrierung fehlgeschlagen! Benutzer existiert bereits!");
        }
        return "register";
    }
}
