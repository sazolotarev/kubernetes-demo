package com.example.kubernetes_demo.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
 
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/health/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/index.html", "/assets/*.css", "/assets/*.js", "/favicon.svg").permitAll()
                        .requestMatchers("/", "/login", "/logout", "/register", "/todo/lists/**").permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}
