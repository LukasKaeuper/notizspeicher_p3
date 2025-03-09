package de.thowl.prog3.exam.web.gui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestFormController {

    @GetMapping("/test")
    public String showTestPage(Model model) {
        // Add the user's name to the model
        model.addAttribute("username", "User's Name"); // Replace with actual user name retrieval logic
        return "test";
    }
}