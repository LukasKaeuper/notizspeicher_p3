package de.thowl.prog3.exam.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.thowl.prog3.exam.storage.entities.User;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class TestUserService {

    @Autowired
    private UserService svc;

    private User user;

    @BeforeEach
    public void setUp() {
        user = createUser();
    }

    private User createUser(){
        user = new User();
        user.setName("TestUser");
        user.setPassword("TestPassword");
        return user;
    }

    @Test
    @DisplayName("Should find user by Name")
    public void testGetUserByName() {
        log.debug("entering testGetUserByName");
        svc.saveUser(user);
        assertTrue(user.getName().equals("TestUser"));
    }

    @Test
    @DisplayName("Should not return an empty user list")
    public void testGetAllUsers() {
        log.debug("entering testGetUser");
        List<User> allUsers = this.svc.getAllUsers();
        assertNotNull(allUsers, "List of all users should not be a null reference");
        assertFalse(allUsers.size() == 0, "List of all users should not be empty");
    }

    @Test
    @DisplayName("Should not throw an exception")
    public void testSaveUser() {
        log.debug("entering testSaveUser");
        User u = User.builder()
                .name("tester")
                .email("tester@mail.com")
                .password("test123")
                .build();
        assertDoesNotThrow(() -> {
            this.svc.saveUser(u);
        });
    }
}
