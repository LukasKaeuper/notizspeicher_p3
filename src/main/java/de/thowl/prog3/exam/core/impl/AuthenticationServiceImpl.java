package de.thowl.prog3.exam.core.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import de.thowl.prog3.exam.core.AuthenticationService;
import de.thowl.prog3.exam.core.InvalidCredentialsException;
import de.thowl.prog3.exam.storage.entities.Session;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.SessionRepository;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thowl.prog3.exam.core.entities.AccessToken;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userstore;
    @Autowired
    private SessionRepository sessionstore;

    public AuthenticationServiceImpl() {

    }

    @Override
    public boolean isValid(String username, String password) {
        log.debug("entering isValid(username={}, password={})", username, password);
        boolean result = false;
        Optional<User> u = this.userstore.findUserByName(username);
        if (null != u) {
            if (u.get().getPassword().equals(password)) {
                result = true;
            }
        }
        log.debug("isValid returns {}", result);
        return result;
    }

    @Override
    public AccessToken login(String username, String password) throws InvalidCredentialsException {
        log.debug("AccessToken login");
        AccessToken result = new AccessToken();
        Optional<User> u = this.userstore.findUserByName(username);
        if ((u.isPresent()) && (u.get().getPassword().equals(password))) {
            // generate access token
            UUID uuid = UUID.randomUUID();
            result.setUSID(uuid.toString());
            result.setLastactive(new Date());
            result.setUser_id(u.get().getId());
            log.debug("login returns {}", result);

            this.sessionstore.save(new Session(result.getUSID(), u.get()));
        } else {
            throw new InvalidCredentialsException();
        }
        return result;
    }

    @Override
    public boolean logout(AccessToken acc) {
        log.debug("entering logout");
        boolean result = false;
        Session s = this.sessionstore.findByToken(acc.getUSID());
        if (null != s) {
            log.info("terminating session " + s.getToken());
            this.sessionstore.delete(s);
            result = true;
        }
        return result;
    }

    @Override
    public Long register(String username, String email, String password) {
        log.debug("entering register");
        User u = new User();
        u.setName(username);
        u.setEmail(email);
        u.setPassword(password);
        log.debug("persisting user " + username + " to storage");
        Long uid = -1l;
        User result = this.userstore.save(u);
        if (result != null) {
            uid = result.getId();
        }
        return uid;
    }
}
