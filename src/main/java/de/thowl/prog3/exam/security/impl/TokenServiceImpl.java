package de.thowl.prog3.exam.security.impl;

import de.thowl.prog3.exam.security.TokenService;
import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public boolean isValidToken(String token) {
        Session session = sessionRepository.findByToken(token);
        return session != null;
    }
}
