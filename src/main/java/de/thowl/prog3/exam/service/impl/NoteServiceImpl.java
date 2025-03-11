package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository repository;

    @Override
    public void saveNote(Note note) {
        repository.save(note);
    }

    @Override
    public List<Note> getNotesbyUser(long userId) {
        return repository.findByUserId(userId);
    }
}
