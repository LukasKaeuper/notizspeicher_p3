package de.thowl.prog3.exam.security.impl;

import java.util.Date;
import java.util.UUID;
import de.thowl.prog3.exam.security.AuthenticationService;
import de.thowl.prog3.exam.security.InvalidCredentialsException;
import de.thowl.prog3.exam.service.SessionService;
import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AuthenticationServiceImpl() {

    }

    @Override
    public boolean isValid(String username, String password) {
        boolean result = false;
        User u = userService.getUser(username);
        if (null != u) {
            if (u.getPassword().equals(password)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public AccessToken login(String username, String password) throws InvalidCredentialsException {
        log.debug("AccessToken login");
        AccessToken result = new AccessToken();
        User u = userService.getUser(username);
        if (u.getPassword().equals(password)) {
            UUID uuid = UUID.randomUUID();
            result.setUSID(uuid.toString());
            result.setLastactive(new Date());
            result.setUser_id(u.getId());
            sessionService.saveSession(result.getUSID(), u);
        } else {
            throw new InvalidCredentialsException();
        }
        return result;
    }

    @Override
    public boolean logout(AccessToken acc) {
        log.debug("entering logout");
        boolean result = false;
        Session s = sessionService.getSessionByToken(acc.getUSID());
        if (null != s) {
            sessionService.deleteSession(s);
            result = true;
        }
        return result;
    }

    @Override
    public void register(String username, String email, String password) {
        log.debug("entering register");
        User u = new User();
        u.setName(username);
        u.setEmail(email);
        u.setPassword(password);
        userService.saveUser(u);
    }
}
