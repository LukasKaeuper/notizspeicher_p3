package de.thowl.prog3.exam.storage.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.thowl.prog3.exam.storage.entities.Note;

public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findByUserId(long userId);

}