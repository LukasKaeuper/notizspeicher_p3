package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Note;

import java.util.List;

public interface NoteService {

    void saveNote(Note note);

    List<Note> getNotesbyUser(long userId);
}
