package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.dto.CategoryDTO;
import de.thowl.prog3.exam.web.dto.NoteDTO;
import de.thowl.prog3.exam.web.dto.UserDTO;
import de.thowl.prog3.exam.web.gui.form.CategoryForm;
import de.thowl.prog3.exam.web.gui.form.FilterForm;
import de.thowl.prog3.exam.web.gui.form.NoteForm;
import de.thowl.prog3.exam.web.mapper.CategoryMapper;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class DashboardFormController {

    @Autowired
    @Qualifier("notemapper")
    private NoteMapper noteMapper = new NoteMapper();

    @Autowired
    @Qualifier("categorymapper")
    private CategoryMapper categoryMapper = new CategoryMapper();

    @Autowired
    private NoteService noteService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model, @RequestParam(required = false) String categoryMessage) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        Long userId = (Long) session.getAttribute("userId");
        List<Note> notes = noteService.getNotesByUser(userId);
        addAttributes(notes, session, model, categoryMessage);
        if (user == null) {
            return "redirect:/login";
        }
        return "/dashboard";
    }

    @PostMapping("/addNote")
    public String saveNote(NoteForm formdata, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        noteService.saveNote(formdata.getTitle(), formdata.getContent(), userId, formdata.getTags(), formdata.getCategory());
        log.debug(formdata.toString());
        return "redirect:/dashboard";
    }

    @PostMapping("/filter")
    public String filter(FilterForm formdata, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        List<Note> filteredNotes= noteService.getFilteredNotes(userId, formdata.getFilterTags(), formdata.getFilterCategory(), formdata.isMustContainAllTags(), formdata.getFilterDateType(), formdata.getFilterDate(), formdata.getFilterNoteType());
        addAttributes(filteredNotes, session, model, null);
        model.addAttribute("filter", formdata);
        log.debug(formdata.toString());
        return "/dashboard";
    }

    @PostMapping("/resetFilter")
    public String resetFilter(HttpSession session, Model model) {
        return "redirect:/dashboard";
    }

    @PostMapping("/addCategory")
    public String addCategory(CategoryForm formdata, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        switch(categoryService.saveCategory(formdata.getCategoryName(), userId, formdata.getCategoryColour())) {
            case "Category created":
                redirectAttributes.addAttribute("categoryMessage", "Kategorie erfolgreich gespeichert!");
                log.debug(redirectAttributes.getAttribute("categoryMessage").toString());
                break;
            case "Category already exists":
                redirectAttributes.addAttribute("categoryMessage", "Kategorie existiert bereits!");
                log.debug(redirectAttributes.getAttribute("categoryMessage").toString());
                break;
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/sharedNote")
    public String viewNote(@RequestParam String shareToken, Model model) {
        try {
            Note note = noteService.getNoteByToken(shareToken);
            NoteDTO noteDTO = noteMapper.map(note);
            model.addAttribute("note", noteDTO);
            return "sharedNote";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    private void addAttributes(List<Note> notes, HttpSession session, Model model, String categoryMessage) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        Long userId = (Long) session.getAttribute("userId");
        List<NoteDTO> NoteDTOs = new ArrayList<>();
        notes.forEach(note -> {NoteDTOs.add(noteMapper.map(note));});
        List<Category> categories = categoryService.getCategoriesByUser(userId);
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        categories.forEach(category -> categoryDTOs.add(categoryMapper.map(category)));
        model.addAttribute("user", user);
        model.addAttribute("notes", NoteDTOs);
        model.addAttribute("categories", categoryDTOs);
        model.addAttribute("categoryMessage", categoryMessage);
        log.debug(NoteDTOs.toString());
        log.debug(categoryDTOs.toString());
    }
}
