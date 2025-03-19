package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        note.setContent(content);
        note.setUserId(userId);
        note.setTags(tags);
        note.setCreatedAt(LocalDateTime.now());
        if (!categoryName.isEmpty()) {
            note.setCategory(categoryService.getCategory(categoryName));
        }
        else {
            note.setCategory(null);
        }
        repository.save(note);
    }

    @Override
    public List<Note> getNotesByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Note> getFilteredNotes(Long userId, List<String> filterTags, String filterCategory, boolean mustContainAllTags, String filterDateType, String filterDate) {
        List<Note> filteredNotes = new ArrayList<>();

        for (Note note : repository.findByUserId(userId)) {
            if (mustContainAllTags){
                if ((filterTags.isEmpty() && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())) ||
                        (!filterTags.isEmpty() && note.getTags().containsAll(filterTags) && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty()))) {
                    filteredNotes.add(note);
                }
            }
            else{
                if ((filterTags.isEmpty() && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())) ||
                        (!filterTags.isEmpty() && !Collections.disjoint(note.getTags(), filterTags) && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty()))) {
                    filteredNotes.add(note);
                }
            }
        }
//        switch(filterDateType){
//            case "before":
//                return filteredNotes;
//            case "after":
//                return filteredNotes;
//            default:
//                return filteredNotes;
//        }
        return filteredNotes;
    }
}
