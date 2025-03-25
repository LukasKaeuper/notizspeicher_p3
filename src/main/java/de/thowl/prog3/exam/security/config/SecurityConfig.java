package de.thowl.prog3.exam.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Die Klasse SecurityConfig stellt einen BCrypt Encoder für Passwoerter zur Verfuegung.
 */
@Configuration
public class SecurityConfig {

    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
