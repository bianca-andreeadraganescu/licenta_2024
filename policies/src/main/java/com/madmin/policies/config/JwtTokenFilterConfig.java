package com.madmin.policies.config;

import com.madmin.policies.services.UserService;
import com.madmin.policies.utils.JwtTokenFilter;
import com.madmin.policies.utils.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenFilterConfig {

    @Bean
    public JwtTokenFilter jwtTokenFilter(UserService userService, JwtTokenProvider tokenProvider) {
        return new JwtTokenFilter(tokenProvider, userService);
    }
}