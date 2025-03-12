package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.dto.UserDTO;
import de.thowl.prog3.exam.web.dto.NoteDTO;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DashboardFormController {

    @Autowired
    @Qualifier("notemapper")
    private NoteMapper mapper = new NoteMapper();

    @Autowired
    private NoteService noteService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        Long userId = (Long) session.getAttribute("userId");
        List<Note> notes = noteService.getNotesbyUser(userId);
        model.addAttribute("notes", notes);
        model.addAttribute("user", user);
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String saveNote(String title, String content, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUserId(userId);
        noteService.saveNote(note);
        return "redirect:/dashboard";
    }
}
