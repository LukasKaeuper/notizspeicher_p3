package de.thowl.prog3.exam.security.config;

import de.thowl.prog3.exam.security.TokenService;
import de.thowl.prog3.exam.security.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(TokenService tokenService) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter(tokenService));
        registrationBean.addUrlPatterns("/protected/*", "/dashboard", "/test"); // Filter to protect endpoint
        return registrationBean;
    }
}
