package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository repository;

    @Override
    public void saveNote(String title, String content, Long userId, List<String> tags, String category) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUserId(userId);
        note.setTags(tags);
        note.setCategory(category);
        repository.save(note);
    }

    @Override
    public List<Note> getNotesByUser(long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Note> getFilteredNotes(Long userId, List<String> filterTags, String filterCategory) {
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note : repository.findByUserId(userId)) {
            if (filterTags.isEmpty() && (filterCategory.equals("disabled") || filterCategory.equals(note.getCategory())) ||
                    !filterTags.isEmpty() && !Collections.disjoint(note.getTags(), filterTags) && (filterCategory.equals("disabled") || filterCategory.equals(note.getCategory()))){
                filteredNotes.add(note);
                //!Collections.disjoint(note.getTags(), filterTags)
                //note.getTags().containsAll(filterTags)
            }
        }
        return filteredNotes;
    }

//    @Override
//    public void addTag(String tag, long userId) {
//
//    }
}
