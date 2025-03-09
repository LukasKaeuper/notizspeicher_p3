package de.thowl.prog3.exam.storage.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.thowl.prog3.exam.storage.entities.Note;

public interface NotizRepository extends CrudRepository<Note, Long> {

    Optional<Note> findNotizById(long id);

    Optional<Note> findNotizByTitle(String title);

    Optional<Note> findNotizByUserId(long userId);
}