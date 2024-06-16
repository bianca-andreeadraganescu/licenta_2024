package com.madmin.policies.config;

import com.madmin.policies.utils.JwtTokenFilter;
import com.madmin.policies.services.AuthenticationService;
import com.madmin.policies.utils.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtTokenFilterConfig {

    @Bean
    public JwtTokenFilter jwtTokenFilter(AuthenticationService authService, UserDetailsService userService, JwtTokenProvider tokenProvider) {
        return new JwtTokenFilter(tokenProvider, userService);
    }
}