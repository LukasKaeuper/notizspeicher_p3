package de.thowl.prog3.exam.security;

import de.thowl.prog3.exam.security.entities.AccessToken;
import jakarta.servlet.http.HttpSession;

public interface AuthenticationService {

    boolean isValid(String username, String password);

    AccessToken login(String username, String password) throws InvalidCredentialsException;

    void logout(String token);

    String register(String username, String email, String password);
}
