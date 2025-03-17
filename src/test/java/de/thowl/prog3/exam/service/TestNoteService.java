package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class TestNoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    private Note note;

    @BeforeEach
    public void setUp() {
        note = createTestNote();
    }

    private Note createTestNote() {
        note = new Note();
        note.setTitle("testTitle");
        note.setContent("testContent");
        note.setUserId(4);
        note.setTags(List.of("testTag1", "testTag2"));
        note.setCategory("testCategory");
        noteRepository.save(note);
        return note;
    }

    @Test
    @DisplayName("Should save a note")
    public void testSaveNote() {
        log.debug("entering testSaveNote");
        noteService.saveNote(note.getTitle(), note.getContent(), note.getUserId(), note.getTags(), note.getCategory());
        List<Note> notes = noteRepository.findByUserId(note.getUserId());
        assertTrue(notes.stream().anyMatch(savedNote ->
                        savedNote.getTitle().equals(note.getTitle()) &&
                        savedNote.getContent().equals(note.getContent()) &&
                        savedNote.getTags().containsAll(note.getTags()) &&
                        savedNote.getCategory().equals(note.getCategory())
        ), "Note should be saved correctly");
    }

    @Test
    @DisplayName("Should return a list of notes")
    public void testGetNotesByUser() {
        log.debug("entering testGetNotesByUser");
        List<Note> notes = noteService.getNotesByUser(note.getUserId());
        assertTrue(notes.stream().anyMatch(savedNote ->
                        savedNote.getTitle().equals(note.getTitle()) &&
                        savedNote.getContent().equals(note.getContent()) &&
                        savedNote.getTags().containsAll(note.getTags()) &&
                        savedNote.getCategory().equals(note.getCategory())
        ), "List of notes should not be empty");
    }

    @Test
    @DisplayName("Should return a list of filtered notes")
    public void testGetFilteredNotes() {
        log.debug("entering testGetFilteredNotes");
        List<Note> filteredNotes = noteService.getFilteredNotes(note.getUserId(), List.of("testTag1"), "testCategory");
        assertTrue(filteredNotes.stream().anyMatch(savedNote ->
                        savedNote.getTitle().equals(note.getTitle()) &&
                        savedNote.getContent().equals(note.getContent()) &&
                        savedNote.getTags().containsAll(note.getTags()) &&
                        savedNote.getCategory().equals(note.getCategory())
        ), "List of filtered notes should not be empty");
    }
}
