package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.SessionService;
import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public boolean isValidToken(String token) {
        Session session = sessionRepository.findByToken(token);
        return session != null;
    }

    @Override
    public void saveSession(String token, User user) {
        Session savedSession = sessionRepository.findByToken(token);
        if (savedSession == null) {
            Session session = new Session(token, user);
            sessionRepository.save(session);
        }

    }

    @Override
    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    @Override
    public Session getSessionByToken(String token) {
        return sessionRepository.findByToken(token);
    }

    @Override
    public void setCurrentToken(String token) {
        Session.currentToken = token;
    }
}
