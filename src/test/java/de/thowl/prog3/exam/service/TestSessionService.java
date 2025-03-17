package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.SessionRepository;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class TestSessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    private User user;
    private Session session;

    @BeforeEach
    public void setUp() {
        sessionRepository.deleteAll();
        userRepository.deleteAll();
        user = createTestUser();
        session = createTestSession(user);
    }

    private User createTestUser() {
        User user = new User();
        user.setName("testUser");
        user.setEmail("testUser@example.com");
        user.setPassword("testPassword");
        userRepository.save(user);
        return user;
    }
    private Session createTestSession(User user) {
        Session session = new Session();
        session.setToken("testToken");
        session.setCreatedAt(new Date());
        session.setUserId(user.getId());
        return session;
    }

    @Test
    @DisplayName("Should validate token correctly")
    public void testValidToken() {
        log.debug("entering testValidToken");
        sessionRepository.save(session);
        boolean valid = sessionService.isValidToken(session.getToken());
        assertTrue(valid, "Token should be valid");
    }

    @Test
    @DisplayName("Should save session correctly")
    public void testSaveSession() {
        log.debug("entering testSaveSession");
        sessionService.saveSession(session.getToken(), user);
        Session savedSession = sessionRepository.findByToken(session.getToken());
        assertTrue(savedSession != null, "Session should be saved");
    }

    @Test
    @DisplayName("Should delete session correctly")
    public void testDeleteSession() {
        log.debug("entering testDeleteSession");
        sessionRepository.save(session);
        sessionService.deleteSession(session);
        Session deletedSession = sessionRepository.findByToken(session.getToken());
        assertTrue(deletedSession == null, "Session should be deleted");
    }

    @Test
    @DisplayName("Should get session by token correctly")
    public void testGetSessionByToken() {
        log.debug("entering testGetSessionByToken");
        sessionRepository.save(session);
        Session foundSession = sessionService.getSessionByToken(session.getToken());
        assertTrue(foundSession != null, "Session should be found");
    }

    @Test
    @DisplayName("Should set current token correctly")
    public void testSetCurrentToken() {
        log.debug("entering testSetCurrentToken");
        sessionService.setCurrentToken(session.getToken());
        assertTrue(Session.currentToken.equals(session.getToken()), "Current token should be set");
    }
}
