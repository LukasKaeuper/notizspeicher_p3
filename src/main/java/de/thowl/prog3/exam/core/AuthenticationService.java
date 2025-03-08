package de.thowl.prog3.exam.core;

import de.thowl.prog3.exam.core.entities.AccessToken;
import de.thowl.prog3.exam.storage.entities.User;

public interface AuthenticationService {
    boolean isValid(String username, String password);

    AccessToken login(String username, String password) throws InvalidCredentialsException;

    boolean logout(AccessToken acc);

    Long register(String username, String email, String password);
}
