package de.thowl.prog3.exam.storage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.thowl.prog3.exam.storage.entities.Note;

public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findByUserId(long userId);

    Optional<Note> findByShareToken(String shareToken);

}