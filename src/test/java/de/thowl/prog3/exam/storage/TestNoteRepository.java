package de.thowl.prog3.exam.storage;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@DataJpaTest
class TestNoteRepository {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        note.setCategory(category);
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
    public void testFindByUserId() {
        log.info("Starting testFindByUserId");
        List<Note> notes = noteRepository.findByUserId(4);
        assertEquals("testTitle", notes.get(0).getTitle(), "Title is wrong");
        assertEquals("testContent", notes.get(0).getContent(), "Content is wrong");
        assertEquals(4, notes.get(0).getUserId(), "UserID is wrong");
        assertEquals(List.of("testTag1", "testTag2"), notes.get(0).getTags(), "Tags are wrong");
        assertEquals("testCategory", notes.get(0).getCategory().getCategoryName(), "Category is wrong");
    }
}