package de.thowl.prog3.exam.security.config;

import de.thowl.prog3.exam.service.SessionService;
import de.thowl.prog3.exam.security.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Die Klasse WebConfig filtert bestimmte HTTP-Anfragen.
 * Sie konfiguriert den AuthenticationFilter für bestimmte URLs,
 * die nicht immer zur Verfuegung stehen sollen, sondern nur mit einem Token.
 * Diese Klasse wurde mit Unterstuetzung von Copilot geschrieben.
 */
@Configuration
public class WebConfig {

    @Bean(name = "authenticationFilter")
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(SessionService sessionService) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter(sessionService));
        registrationBean.addUrlPatterns("/dashboard"); // geschützte URLs
        return registrationBean;
    }
}
