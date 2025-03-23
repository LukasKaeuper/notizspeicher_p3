package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.entities.User;

public interface SessionService {

    boolean isValidToken(String token);

    void saveSession(String token, User user);

    void deleteSession(Session session);

    Session getSessionByToken(String token);
}
