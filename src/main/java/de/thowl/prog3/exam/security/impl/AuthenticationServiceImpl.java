package de.thowl.prog3.exam.security.impl;

import java.util.Date;
import java.util.UUID;
import de.thowl.prog3.exam.security.AuthenticationService;
import de.thowl.prog3.exam.service.SessionService;
import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import de.thowl.prog3.exam.security.entities.AccessToken;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl() {

    }

    @Override
    public boolean isValid(String username, String password) {
        boolean result = false;
        User u = userService.getUser(username);
        if (null != u) {
            if (passwordEncoder.matches(password, u.getPassword())) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public AccessToken login(String username, String password) {
        log.debug("AccessToken login");
        AccessToken result = new AccessToken();
        User u = userService.getUser(username);
        UUID uuid = UUID.randomUUID();
        result.setUSID(uuid.toString());
        result.setLastactive(new Date());
        result.setUser_id(u.getId());
        sessionService.saveSession(result.getUSID(), u);
        return result;
    }

    @Override
    public void logout(String token) {
        log.debug("entering logout");
        Session s = sessionService.getSessionByToken(token);
        if (null != s) {
            sessionService.deleteSession(s);
        }
    }

    @Override
    public String register(String username, String email, String password) {
        log.debug("entering register");
        if (username.length() < 5 || password.length() < 5){
            log.error("validation error");
            return "validation error";
        }
        try {
            User u = new User();
            u.setName(username);
            u.setEmail(email);
            String hashedPassword = new BCryptPasswordEncoder().encode(password);
            u.setPassword(hashedPassword);
            userService.saveUser(u);
            return "user registered";
        } catch (DataIntegrityViolationException e) {
            log.error("Registration failed: Duplicate username or email", e);
            return "duplicate error";
        } catch (Exception e) {
            log.error("Registration failed", e);
            return "registration failed";
        }
    }
}
