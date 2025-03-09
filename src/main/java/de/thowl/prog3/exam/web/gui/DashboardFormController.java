package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.web.dto.UserDTO;
import org.springframework.ui.Model;
import de.thowl.prog3.exam.storage.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardFormController {

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "dashboard";
    }
}
