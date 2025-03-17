package de.thowl.prog3.exam.storage;

import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.SessionRepository;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataJpaTest
public class TestSessionRepository {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

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
        user = new User();
        user.setName("testUser");
        user.setEmail("testUser@example.com");
        user.setPassword("testPassword");
        userRepository.save(user);
        return user;
    }

    private Session createTestSession(User user) {
        session = new Session();
        session.setToken("testToken");
        session.setCreatedAt(new Date());
        session.setUserId(user.getId());
        sessionRepository.save(session);
        return session;
    }

    @Test
    public void testFindByToken() {
        log.info("Starting testFindByToken");
        Session foundSession = sessionRepository.findByToken("testToken");
        assertNotNull(foundSession, "Session should not be null");
        assertEquals("testToken", foundSession.getToken(), "Token is wrong");
    }
}
