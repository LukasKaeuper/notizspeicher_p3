package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void saveNote(String title, String content, Long userId, List<String> tags, String categoryName) {
        Note note = new Note();
        note.setTitle(title);
        note.setUserId(userId);
        note.setTags(tags);
        note.setCreatedAt(LocalDateTime.now());
        String shareToken = generateToken();
        note.setShareToken(shareToken);
        note.setShareLink(generateLink(shareToken));
        if (!categoryName.isEmpty()) {
            note.setCategory(categoryService.getCategory(categoryName, userId));
        }
        else {
            note.setCategory(null);
        }
        try {
            URL url = new URL(content);
            note.setContent(url.toExternalForm());
            note.setType("link");
        } catch (MalformedURLException e) {
            note.setType("text");
            note.setContent(content);
        }
        repository.save(note);
    }

    @Override
    public List<Note> getNotesByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Note> getFilteredNotes(Long userId, List<String> filterTags, String filterCategory, boolean mustContainAllTags, String filterDateType, String filterDate, String filterNoteType) {
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note : repository.findByUserId(userId)) {
            log.debug("Note Type: " + note.getType());
            if (mustContainAllTags){
                if ((filterNoteType.equals(note.getType()) || filterNoteType.equals("disabled")) && ((filterTags.isEmpty() && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())) ||
                        (!filterTags.isEmpty() && note.getTags().containsAll(filterTags) && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())))) {
                    filteredNotes.add(note);
                }
            }
            else{
                if ((filterNoteType.equals(note.getType()) || filterNoteType.equals("disabled")) && ((filterTags.isEmpty() && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())) ||
                        (!filterTags.isEmpty() && !Collections.disjoint(note.getTags(), filterTags) && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())))) {
                    filteredNotes.add(note);
                }
            }
            if (!filterDateType.equals("disabled") && !filterDate.isEmpty()) {
                LocalDateTime filterDateObject = LocalDateTime.parse(filterDate);
                switch(filterDateType){
                    case "before":
                        if (filterDateObject.isBefore(note.getCreatedAt())) {
                            filteredNotes.remove(note);
                        }
                        break;
                    case "after":
                        if (filterDateObject.isAfter(note.getCreatedAt())) {
                            filteredNotes.remove(note);
                        }
                        break;
                }
            }
        }
        return filteredNotes;
    }

    @Override
    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    @Override
    public String generateLink(String token){
        return "http://localhost:8080/sharedNote?shareToken=" + token;
    }

    @Override
    public Note getNoteByToken(String token){
        return repository.findByShareToken(token).orElseThrow();
    }
}
