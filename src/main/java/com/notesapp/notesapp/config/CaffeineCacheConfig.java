package com.notesapp.notesapp.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.notesapp.notesapp.dto.UserLoginActivityDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
class CaffeineCacheConfig {

    @Bean
    public Cache<String, Integer> createUnsuccessfulLoginAttemptsCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    @Bean
    public Cache<UserDetails, List<UserLoginActivityDto>> userLoginActivityCache() {
        return Caffeine.newBuilder()
                .build();
    }

}
