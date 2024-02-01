package com.sparta.todotodo.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean // bean 수동등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}