package de.thowl.prog3.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public User getUser(long id) {
        log.debug("entering getUser(id={})", id);
        Optional<User> result = this.repository.findById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public User getUser(String name) {
        log.debug("entering getUser(name={})", name);
        Optional<User> result = this.repository.findUserByName(name);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public User getUserWithPassword(String name, String password) {
        log.debug("entering getUser(name={}, password={})", name, password);
        Optional<User> result = this.repository.findUserByNameAndPassword(name, password);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("entering getAllUsers()");
        ArrayList<User> result = new ArrayList<>();
        for (User u : this.repository.findAll()) {
            result.add(u);
        }
        return result;
    }

}