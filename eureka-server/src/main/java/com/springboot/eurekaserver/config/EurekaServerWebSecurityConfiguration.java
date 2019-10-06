package com.springboot.eurekaserver.config;

import com.microservice.authentication.common.service.SharedAuthenticationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity(debug = true)
@AllArgsConstructor
public class EurekaServerWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] WHITELIST = {
        "/eureka/apps/**",
        "/actuator/**",
        "/v1/agent/self",
        "/eureka/peerreplication/batch/**",
        "/v1/catalog/services",
        "/v1/catalog/service/**",
        "/**/*.js",
        "/**/*.css",
        "/**/*.html",
        "/favicon.ico",
    };

    private final SharedAuthenticationServiceImpl sharedAuthenticationService;

    @Override
    protected UserDetailsService userDetailsService() {
        return sharedAuthenticationService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .anyRequest().hasRole("ADMIN")
            .and()
            .formLogin()
            .and()
            .logout();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(WHITELIST);
    }
}
