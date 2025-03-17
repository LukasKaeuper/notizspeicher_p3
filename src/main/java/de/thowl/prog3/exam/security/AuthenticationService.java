package de.thowl.prog3.exam.security;

import de.thowl.prog3.exam.security.entities.AccessToken;

public interface AuthenticationService {

    boolean isValid(String username, String password);

    AccessToken login(String username, String password) throws InvalidCredentialsException;

    boolean logout(AccessToken acc);

    String register(String username, String email, String password);
}
