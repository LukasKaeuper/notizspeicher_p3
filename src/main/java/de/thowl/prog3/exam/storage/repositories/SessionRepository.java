package de.thowl.prog3.exam.storage.repositories;

import de.thowl.prog3.exam.storage.entities.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {

    public Session findByToken(String token);
};
