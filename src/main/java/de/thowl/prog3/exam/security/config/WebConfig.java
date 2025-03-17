package de.thowl.prog3.exam.security.config;

import de.thowl.prog3.exam.service.SessionService;
import de.thowl.prog3.exam.security.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebConfig {

    @Bean(name = "authenticationFilter")
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(SessionService sessionService) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter(sessionService));
        registrationBean.addUrlPatterns("/dashboard", "/test"); // Filter to protect endpoint
        return registrationBean;
    }
}
