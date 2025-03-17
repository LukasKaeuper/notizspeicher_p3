package de.thowl.prog3.exam.storage;

import de.thowl.prog3.exam.storage.entities.Note;
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
    public void testFindByUserId() {
        log.info("Starting testFindByUserId");
        List<Note> notes = noteRepository.findByUserId(4);
        assertEquals("testTitle", notes.get(0).getTitle(), "Title is wrong");
    }
}