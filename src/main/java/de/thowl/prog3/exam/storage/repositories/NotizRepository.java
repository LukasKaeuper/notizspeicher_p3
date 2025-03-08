package de.thowl.prog3.exam.storage.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.thowl.prog3.exam.storage.entities.Notiz;

public interface NotizRepository extends CrudRepository<Notiz, Long> {

    Optional<Notiz> findNotizById(long id);

    Optional<Notiz> findNotizByTitle(String title);

    Optional<Notiz> findNotizByUserId(long userId);
}