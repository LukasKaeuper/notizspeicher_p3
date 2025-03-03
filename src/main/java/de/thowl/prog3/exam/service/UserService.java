package de.thowl.prog3.exam.service;

import java.util.List;

import de.thowl.prog3.exam.storage.entities.User;

public interface UserService {

    public User getUser(long id);

    public User getUser(String name);

    public User getUserWithPassword(String name, String password);

    public List<User> getAllUsers();


}