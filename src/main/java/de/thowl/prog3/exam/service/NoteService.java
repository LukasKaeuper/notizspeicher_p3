package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Note;

import java.util.List;

public interface NoteService {

    void saveNote(String title, String content, Long userId, List<String> tags, String category);

    List<Note> getNotesByUser(Long userId);

    List<Note> getFilteredNotes(Long userId, List<String> tags, String category, boolean mustContainAllTags, String filterDateType, String filterDate, String filterNoteType);

    String generateToken();

    String generateLink(String token);

    Note getNoteByToken(String token);
}
