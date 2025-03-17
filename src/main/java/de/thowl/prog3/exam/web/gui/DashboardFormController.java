package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.dto.NoteDTO;
import de.thowl.prog3.exam.web.dto.UserDTO;
import de.thowl.prog3.exam.web.gui.form.NoteForm;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardFormController {

    @Autowired
    @Qualifier("notemapper")
    private NoteMapper noteMapper = new NoteMapper();

    @Autowired
    private NoteService noteService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        Long userId = (Long) session.getAttribute("userId");
        List<Note> notes = noteService.getNotesByUser(userId);
        List<NoteDTO> noteDTOs = new ArrayList<>();
        notes.forEach(note -> {noteDTOs.add(noteMapper.map(note));});
        model.addAttribute("notes", noteDTOs);
        model.addAttribute("user", user);
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "/dashboard";
    }

    @PostMapping("/addNote")
    public String saveNote(NoteForm formdata, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        noteService.saveNote(formdata.getTitle(), formdata.getContent(), userId, formdata.getTags(), formdata.getCategory());
        return "redirect:/dashboard";
    }

    @PostMapping("/filter")
    public String filter(NoteForm formdata, HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        Long userId = (Long) session.getAttribute("userId");
        List<Note> filteredNotes= noteService.getFilteredNotes(userId, formdata.getFilterTags(), formdata.getFilterCategory());
        List<NoteDTO> filteredNoteDTOs = new ArrayList<>();
        filteredNotes.forEach(note -> {filteredNoteDTOs.add(noteMapper.map(note));});
        model.addAttribute("notes", filteredNoteDTOs);
        model.addAttribute("user", user);
        return "/dashboard";
    }
}
