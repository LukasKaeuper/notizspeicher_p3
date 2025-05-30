package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class TestNoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NoteService noteService;

    private Category category;
    private Note note;

    @BeforeEach
    public void setUp() {
        noteRepository.deleteAll();
        categoryRepository.deleteAll();
        category = createTestCategory();
        note = createTestNote();
    }

    private Note createTestNote() {
        note = new Note();
        note.setTitle("testTitle");
        note.setContent("testContent");
        note.setUserId(4L);
        note.setTags(List.of("testTag1", "testTag2"));
        note.setType("text_link_image");
        note.setCategory(category);
        note.setLink("https://test.com");
        noteRepository.save(note);
        return note;
    }

    private Category createTestCategory() {
        category = new Category();
        category.setCategoryName("testCategory");
        category.setUserId(4L);
        categoryRepository.save(category);
        return category;
    }

    @Test
    @DisplayName("Should save a note")
    public void testSaveNote() {
        log.debug("entering testSaveNote");
        byte[] testImageData = {0x1, 0x2, 0x3};
        MultipartFile file = new MockMultipartFile("empty", testImageData);
        noteService.saveNote(note.getTitle(), note.getContent(), note.getUserId(), note.getTags(), category.getCategoryName(), file, note.getLink());
        List<Note> notes = noteRepository.findByUserId(note.getUserId());
        assertTrue(notes.stream().anyMatch(savedNote ->
                        savedNote.getTitle().equals(note.getTitle()) &&
                        savedNote.getContent().equals(note.getContent()) &&
                        savedNote.getTags().containsAll(note.getTags()) &&
                        savedNote.getCategory().getCategoryName().equals(note.getCategory().getCategoryName()) &&
                        Arrays.equals(savedNote.getImage(), testImageData) &&
                        savedNote.getLink().equals(note.getLink())
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
                        savedNote.getCategory().getCategoryName().equals(note.getCategory().getCategoryName())
        ), "List of notes should not be empty");
    }

    @Test
    @DisplayName("Should return a list of filtered notes")
    public void testGetFilteredNotes() {
        log.debug("entering testGetFilteredNotes");
        List<Note> filteredNotes = noteService.getFilteredNotes(note.getUserId(), List.of("testTag1"),
                "testCategory", false, "disabled", "", true, true, true, "");
        assertTrue(filteredNotes.stream().anyMatch(savedNote ->
                        savedNote.getTitle().equals(note.getTitle()) &&
                        savedNote.getContent().equals(note.getContent()) &&
                        savedNote.getTags().containsAll(note.getTags()) &&
                        savedNote.getType().equals(note.getType()) &&
                        savedNote.getCategory().getCategoryName().equals(note.getCategory().getCategoryName())
        ), "List of filtered notes should not be empty");
    }
}
